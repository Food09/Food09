package com.example.project

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
    private val chatUserRef : DatabaseReference = rootRef.getReference("ChatUser")
    private lateinit var chatRef : DatabaseReference
    private var chatList = arrayListOf<ChatModel>()
    private lateinit var chatChannelKey : String
    private lateinit var chatAdapter : ChatAdapter
    private var currentNickName: String = "charlie"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        init()
    }

//    fun init(){
//        val user : DatabaseReference = chatUserRef.child(currentNickName)
//        if (user == null) {
//            Log.d("ChatListFragment", "Failed to find the user in ChatUser")
//        }
//
//        user.addListenerForSingleValueEvent(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                chatKey = snapshot.value.toString()
//                Log.d("ChatListFragment", chatKey.toString())
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("ChatListFragment", "Failed to find the user in ChatUser (reading data)")
//            }
//        })
//    }

    fun findChat() : Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_chat_list, container, false)

        // Todo: 닉네임 초기화 수정
        currentNickName = "charlie"


        val notify = rootView.findViewById(R.id.notifyTextView_chat!!) as TextView
        val chatContent = rootView.findViewById(R.id.et_chatting) as TextView

        // Todo: 현재 유저가 있는 채팅방 찾기 -> chatRef 초기화
//        if ( findChat() == false ){
//            notify.text = "공구에 참여했을 경우에 채팅창이 활성화됩니다."
//            return rootView
//        }

        notify.text = ""

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

            // add chat data to firebase
            val chatKey : String? = chatRef.push().key
            val now = System.currentTimeMillis()
            val dateTime : String? = SimpleDateFormat("yyyy-MM-dd.HH:mm:ss", Locale.KOREAN).format(now)

            if (chatKey != null && (dateTime) != null) {
                val chat : ChatModel = ChatModel(chatKey, currentNickName, chatContent.text.toString(), dateTime)
                val chatValues = chat.toMap()
                Log.d("ChatListFragment", chatValues.toString())
                val childUpdates = hashMapOf<String, Any>(
                    "/${chatKey}" to chatValues
                )
                chatRef.updateChildren(childUpdates)

            }
            readChat()
            recyclerView.smoothScrollToPosition(chatList.size)
        }

        return rootView
    }

    private fun readChat(){
        // find key
        // 현재 유저의 닉네임으로 참여하고 있는 채팅방 찾기
//        Log.d("ChatListFragment !!! chatKey ! ", chatKey.toString())


//        val user = chatUserRef.equalTo(currentNickName)
        val user = chatUserRef.child(currentNickName)
//
        user.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatChannelKey = snapshot.value.toString()
                Log.d("ChatListFragment chatChannelKey", chatChannelKey)
                chatRef = rootChatRef.child(chatChannelKey)

                val chatQuery = chatRef.orderByChild("dateTime")

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


    }

}