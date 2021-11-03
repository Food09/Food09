package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ChatListFragment : Fragment() {

    private var chatRef : DatabaseReference = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Chat")
    private val chatList = arrayListOf<ChatModel>()
    private lateinit var chatAdapter : ChatAdapter
    private lateinit var currentNickName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_chat_list, container, false)

        currentNickName = "charlie"

        // RecyclerView 설정
        var recyclerView = rootView.findViewById(R.id.recyclerView_chat!!) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatAdapter = ChatAdapter(currentNickName, chatList)

        // 메시지 발송 버튼
        var btnSend = rootView.findViewById(R.id.btn_send!!) as Button

        btnSend.setOnClickListener {
            Log.d("ChatListFragment", "btn clicked!")

            // ToDo: add chat data to firebase
            // readChat()
        }

        // readChat()


//        recyclerView.addItemDecoration(ArticleItemDecorator(10))



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}