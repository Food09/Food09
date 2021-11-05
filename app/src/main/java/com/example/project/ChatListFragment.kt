package com.example.project

import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_chat_list.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*



class ChatListFragment : Fragment() {
    private val rootRef : FirebaseDatabase = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val rootChatRef : DatabaseReference = rootRef.getReference("Chat")
    private val rootChatUserRef : DatabaseReference = rootRef.getReference("ChatUser")
    private lateinit var chatChannelRef : DatabaseReference
    private var chatList = arrayListOf<ChatModel>()
    private lateinit var chatChannelKey : String
    private lateinit var chatAdapter : ChatAdapter
    private lateinit var currentNickName: String
    private lateinit var userInfo : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MainActivity로부터 사용자 정보 받아오기
        if (getArguments() == null){
            return
        }
        userInfo = requireArguments()!!.getSerializable("userInfo") as UserModel
        Log.d("ChatListFragment getArguments : ", userInfo.nickName)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_chat_list, container, false)

        // 닉네임 초기화
        currentNickName = userInfo.nickName


        val chatContent = rootView.findViewById(R.id.et_chatting) as TextView

        // RecyclerView 설정
        var recyclerView = rootView.findViewById(R.id.recyclerView_chat!!) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatAdapter = ChatAdapter(currentNickName, chatList)
        recyclerView.adapter = chatAdapter

        readChat()




        // 메시지 발송 버튼
        var btnSend = rootView.findViewById(R.id.btn_send!!) as Button
        btnSend.isEnabled = true

        btnSend.setOnClickListener {
            Log.d("ChatListFragment", "btn clicked!")

            if (TextUtils.isEmpty(et_chatting.text.toString().trim())){
                return@setOnClickListener
            }

            // add chat data to firebase
            val chatKey : String? = chatChannelRef.push().key
            val now = System.currentTimeMillis()
            val dateTime : String? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)

            if (chatKey != null && (dateTime) != null) {
                val chat : ChatModel = ChatModel(chatKey, currentNickName, chatContent.text.toString(), dateTime)
                val chatValues = chat.toMap()
                Log.d("ChatListFragment", chatValues.toString())
                val childUpdates = hashMapOf<String, Any>(
                    "/${chatKey}" to chatValues
                )
                chatChannelRef.updateChildren(childUpdates)

            }
            readChat()
            recyclerView.smoothScrollToPosition(chatList.size)
            et_chatting.setText("")
        }

        return rootView
    }

    private fun readChat(){
        // 현재 유저의 닉네임으로 참여하고 있는 채팅방 찾기
        // 해당 채팅방 내용 읽어오기
        val user = rootChatUserRef.child(currentNickName)
        user.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                notifyTextView_chat.text = ""

                chatChannelKey = snapshot.value.toString()
                Log.d("ChatListFragment chatChannelKey", chatChannelKey)
                chatChannelRef = rootChatRef.child(chatChannelKey)

                val chatQuery = chatChannelRef.orderByChild("dateTime")

                chatQuery.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatList.clear()
                        for (chat in snapshot.children) {
                            Log.d("ChatListFragment", chat.key.toString())
                            val chatKey: String = chat.key.toString()
                            val nickName: String = chat.child("nickName").value.toString()
                            val content: String = chat.child("content").value.toString()
                            val dataTime: String = chat.child("dateTime").value.toString()
                            chatList.add(ChatModel(chatKey, nickName, content, dataTime))
                        }
                        chatAdapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(
                            "ChatListFragment",
                            "Failed to find the user in ChatUser (reading data)"
                        )
                    }
                })
            }
//
//        Log.d("ChatListFragment chatKey ! ", user.key toString())
//        user.
//        Log.d("ChatListFragment chatKey ! ", user.get().toString())
//        Log.d("ChatListFragment", "Find the chatKey in ChatUser! " + chatRef.toString())


//        chatRef = rootChatRef.child("chat1")
//        val chatQuery = chatRef.orderByChild("dateTime")
//
//        chatQuery.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
////                var tmpChatList = ArrayList<ChatModel>()
//                chatList.clear()
//                for ( chat in snapshot.children) {
//                    Log.d("ChatListFragment", chat.key.toString())
//                    val chatKey : String = chat.key.toString()
//                    val nickName : String = chat.child("nickName").value.toString()
//                    val content : String = chat.child("content").value.toString()
//                    val dataTime : String = chat.child("dateTime").value.toString()
////                    tmpChatList.add(ChatModel(chatKey, nickName, content, dataTime))
//                    chatList.add(ChatModel(chatKey, nickName, content, dataTime))
//                }
////                chatList.reverse()
////                chatList = tmpChatList
////                chatAdapter.replaceList(chatList)
//                chatAdapter.notifyDataSetChanged()
//            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ChatListFragment", "Failed to read Database")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 채팅방 나가기 버튼 이벤트 리스너 설정
        chat_exit_btn.setOnClickListener {
            Log.d("ChatListFragment", "Exit Button Clicked!")
            // ChatUser DB 상에서 현재 유저 데이터 삭제
            rootChatUserRef.child(userInfo.nickName).setValue(null)

            // Chat에서 나갔습니다 메시지 남기기
            // 채팅방 나가기 알림 메시지
            val chatKey = chatChannelRef.push().key.toString()
            val now = System.currentTimeMillis()
            val dateTime : String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)
            val chat : ChatModel = ChatModel(chatKey, "알림", userInfo.nickName + "님이 퇴장하셨습니다.", dateTime)
            val chatValues = chat.toMap()
            val chatUpdates = hashMapOf<String, Any>(
                "/${chatKey}" to chatValues
            )
            chatChannelRef.updateChildren(chatUpdates)
        }
    }

}