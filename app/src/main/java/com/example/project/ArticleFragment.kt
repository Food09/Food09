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

        // 버튼 비활성화
        editButton.isEnabled = false
        deleteButton.isEnabled = false
        // 버튼 가리기
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

            // 음식 이미지 보이기
            val tmpImageRef : StorageReference = imageRef.child(article.imageUrls[0])
            tmpImageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    Glide.with(articleImage.context).load(it.result).centerCrop().placeholder(R.drawable.ic_baseline_fastfood_24).into(articleImage)
                }
            }

            // 버튼 작성자에게만 보이게하고 활성화하기
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
            // 현재 사용자가 이미 해당 채팅방에 있는지 확인하기
            val chatUserRef : DatabaseReference = rootDB.getReference("ChatUser")

            chatUserRef.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for ( user in snapshot.children ){
                        if (user.key.toString() == userInfo.nickName) {
                            if (user.value.toString() == article.articleKey) {
                                // 이미 해당 방에 있을 때
                                Snackbar.make(view!!, "해당 채팅방에 이미 입장해있습니다.", Snackbar.LENGTH_SHORT).setAction("action", null).show()
                                return
                            } else {
                                // 다른 방에 있을 때
                                Snackbar.make(view!!, "이미 다른 채팅방에 입장해있습니다.", Snackbar.LENGTH_SHORT).setAction("action", null).show()
                                return
                            }
                        }
                    }
                    // 참여하고 있는 채팅방이 없을 때
                    // (현재사용자, articleKey) 데이터를 ChatUser Firebase에 추가하기
                    chatUserRef.child(userInfo.nickName).setValue(article.articleKey)

                    // 채팅방 접속 알림 메시지
                    val chatRef : DatabaseReference = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Chat")
                    val chatKey = chatRef.push().key.toString()
                    val now = System.currentTimeMillis()
                    val dateTime : String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)
                    val chat : ChatModel = ChatModel(chatKey, "알림", userInfo.nickName + "님이 입장했습니다.", dateTime)
                    val chatValues = chat.toMap()
                    val chatUpdates = hashMapOf<String, Any>(
                        "/${article.articleKey}/${chatKey}" to chatValues
                    )
                    chatRef.updateChildren(chatUpdates)

                    // Article 디비의 멤버에 추가함? -> 굳이 안해도 됨
                    // Article 디비의 인원수 변경 -> 일단 스킵

                    // 참여하기 버튼 누르면 채팅 창으로 이동
                    // ChatListFragment로 이동
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


        // 수정 버튼
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

            // Article DB에서 삭제
            val articleRef : DatabaseReference = rootDB.getReference("Article")
            articleRef.child(article.articleKey).removeValue()

            // Chat DB도 삭제
            val chatRef : DatabaseReference = rootDB.getReference("Chat")
            chatRef.child(article.articleKey).removeValue()

            // ChatUser DB에서 사용자들 모두 삭제
            // 리스너 등록
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