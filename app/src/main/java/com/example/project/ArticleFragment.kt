package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class ArticleFragment : Fragment() {
    private val rootDB : FirebaseDatabase = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private lateinit var article : ArticleModel
    private lateinit var userInfo : UserModel
    private val storage : FirebaseStorage = Firebase.storage("gs://food09-581c6.appspot.com/")
    private val imageRef : StorageReference = storage.getReference("Images")
    private var isAuthor : Boolean = false

//    private lateinit var callback: OnBackPressedCallback
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                Log.d("ArticleFragment", "BackPressedCallback Called!")
//
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        callback.remove()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_article, container, false)

        val userID : TextView = rootView!!.findViewById(R.id.userIDTextView_article)
        val userProfile : TextView = rootView!!.findViewById(R.id.userProfileTextView_article)
        val title : TextView = rootView!!.findViewById(R.id.titleTextView_article)
        val content : TextView = rootView!!.findViewById(R.id.contentTextView_article)
        val userNumber : TextView = rootView!!.findViewById(R.id.userNumberTextView_article)
        val enterButton : Button = rootView!!.findViewById(R.id.buttonEnter_article)
        val articleImage : ImageView = rootView!!.findViewById(R.id.article_image)
        val editButton : Button = rootView!!.findViewById(R.id.third)
        val deleteButton : Button = rootView!!.findViewById(R.id.second)

        // ?????? ????????????
        editButton.isEnabled = false
        deleteButton.isEnabled = false
        // ?????? ?????????
        editButton.visibility = View.INVISIBLE
        deleteButton.visibility = View.INVISIBLE

        if (getArguments() != null){
//            var data : String? = requireArguments().getString("test")
//            Log.d("ArticleFragment", "data : " + data)
            article = requireArguments()!!.getSerializable("articleInfo") as ArticleModel
            Log.d("ArticleFragment", "ArticleInfo : " + article.get_userID())
            userInfo = requireArguments()!!.getSerializable("userInfo") as UserModel

            userID.text = article.get_userID()
            userProfile.text = article.get_userProfile()
            title.text = article.get_title()
            content.text = article.get_content()
            userNumber.text = article.get_curNum().toString() + " / " + article.get_maxNum().toString()

            // ?????? ????????? ?????????
            val tmpImageRef : StorageReference = imageRef.child(article.imageUrls[0])
            tmpImageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    Glide.with(articleImage.context).load(it.result).centerCrop().placeholder(R.drawable.ic_baseline_fastfood_24).into(articleImage)
                }
            }

            // ?????? ?????????????????? ??????????????? ???????????????
            if (userInfo.nickName == article.userID){
                isAuthor = true
                editButton.isEnabled = true
                deleteButton.isEnabled = true
                editButton.visibility = View.VISIBLE
                deleteButton.visibility = View.VISIBLE
            }
        }

        enterButton.setOnClickListener {
            Log.d("ArticleFragment", "enterButton Clicked!")
            // ?????? ???????????? ?????? ?????? ???????????? ????????? ????????????
            val chatUserRef : DatabaseReference = rootDB.getReference("ChatUser")

            chatUserRef.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for ( user in snapshot.children ){
                        if (user.key.toString() == userInfo.nickName) {
                            if (user.value.toString() == article.articleKey) {
                                // ?????? ?????? ?????? ?????? ???
                                Snackbar.make(view!!, "?????? ???????????? ?????? ?????????????????????.", Snackbar.LENGTH_SHORT).setAction("action", null).show()
                                return
                            } else {
                                // ?????? ?????? ?????? ???
                                Snackbar.make(view!!, "?????? ?????? ???????????? ?????????????????????.", Snackbar.LENGTH_SHORT).setAction("action", null).show()
                                return
                            }
                        }
                    }
                    // ???????????? ?????? ???????????? ?????? ???
                    // (???????????????, articleKey) ???????????? ChatUser Firebase??? ????????????
                    chatUserRef.child(userInfo.nickName).setValue(article.articleKey)

                    // ????????? ?????? ?????? ?????????
                    val chatRef : DatabaseReference = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Chat")
                    val chatKey = chatRef.push().key.toString()
                    val now = System.currentTimeMillis()
                    val dateTime : String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)
                    val chat : ChatModel = ChatModel(chatKey, "??????", userInfo.nickName + "?????? ??????????????????.", dateTime)
                    val chatValues = chat.toMap()
                    val chatUpdates = hashMapOf<String, Any>(
                        "/${article.articleKey}/${chatKey}" to chatValues
                    )
                    chatRef.updateChildren(chatUpdates)

                    // Article ????????? ????????? ?????????? -> ?????? ????????? ???
                    // Article ????????? ????????? ?????? -> ?????? ??????

                    // ???????????? ?????? ????????? ?????? ????????? ??????
                    // ChatListFragment??? ??????
                    val bundle : Bundle = Bundle()
                    bundle.putSerializable("userInfo", userInfo)
                    val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                    val chatListFragment : ChatListFragment = ChatListFragment()
                    chatListFragment.setArguments(bundle)
                    transaction.replace(R.id.fragementContainer, chatListFragment).commit()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("ArticleFragment","Not yet implemented")
                }
            })

        }


        // ?????? ??????
        editButton.setOnClickListener {
            Log.d("ArticleFragment", "editButton Clicked!")

            val bundle : Bundle = Bundle()
            bundle.putSerializable("userInfo", userInfo)
            bundle.putSerializable("articleInfo", article)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            val homeFragment : HomeFragment = HomeFragment()
            val editArticleFragment : EditArticleFragment = EditArticleFragment()
            editArticleFragment.setArguments(bundle)
            homeFragment.setArguments(bundle)
            parentFragmentManager.popBackStack()
            transaction.replace(R.id.fragementContainer, homeFragment).add(R.id.fragementContainer, editArticleFragment).addToBackStack(null).commit()
        }

        deleteButton.setOnClickListener {
            Log.d("ArticleFragment", "deleteButton Clicked!")

            // Article DB?????? ??????
            val articleRef : DatabaseReference = rootDB.getReference("Article")
            articleRef.child(article.articleKey).removeValue()

            // Chat DB??? ??????
            val chatRef : DatabaseReference = rootDB.getReference("Chat")
            chatRef.child(article.articleKey).removeValue()

            // ChatUser DB?????? ???????????? ?????? ??????
            // ????????? ??????
            val chatUserRef : DatabaseReference = rootDB.getReference("ChatUser")
            chatUserRef.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    for ( user in snapshot.children ){
                        if (user.value.toString() == article.articleKey){
                            chatUserRef.child(user.key.toString()).removeValue()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("ArticleFragment", "Failed to read ChatUser")
                }
            })

            parentFragmentManager.popBackStack()
        }

        return rootView
    }
}