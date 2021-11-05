package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class ArticleFragment : Fragment() {
    val rootDB : FirebaseDatabase = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/")
    lateinit var article : ArticleModel
    lateinit var userInfo : UserModel
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
        }

        enterButton.setOnClickListener {
            Log.d("ArticleFragment", "enterButton Clicked!")
            // (현재사용자, articleKey) 데이터를 ChatUser Firebase에 추가하기
            val chatUserRef : DatabaseReference = rootDB.getReference("ChatUser")
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


            // ToDo: Article 디비의 인원수 변경


            // 참여하기 버튼 누르면 채팅 창으로 이동
            // ChatListFragment로 이동
            val bundle : Bundle = Bundle()
            bundle.putSerializable("userInfo", userInfo)
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            val chatListFragment : ChatListFragment = ChatListFragment()
            chatListFragment.setArguments(bundle)
            transaction.replace(R.id.fragementContainer, chatListFragment).commit()
        }

        return rootView
    }


}