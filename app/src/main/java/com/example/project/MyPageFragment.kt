package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import java.text.SimpleDateFormat
import java.util.*


class MyPageFragment : Fragment() {

    private lateinit var userInfo : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MainActivity로부터 사용자 정보 받아오기
        if (requireArguments().containsKey("userInfo")){
            userInfo = requireArguments()!!.getSerializable("userInfo") as UserModel
            Log.d("MyPageFragment getArguments : ", userInfo.nickName)


        }
        // userInfo.nickName
        // userInfo.email
        // userInfo.profile
        // userInfo.dateTime 가입 날짜 및 시간
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_page_userName.text = userInfo.nickName
        my_page_userContentEdit.setText(userInfo.profile)
        my_page_userEnterDate.text = userInfo.dateTime
        my_page_email.text = userInfo.email
        my_page_userContentEdit_btn.setOnClickListener {
            Log.d("MyPageFragment", "수정하기 Clicked!")
            my_page_userContentEdit.isCursorVisible = false
            my_page_userContentEdit.isFocusable = false
//            my_page_userContentEdit.setRawInputType(EditorInfo.TYPE_NULL)

            // User DB 정보 업데이트
            // 채팅방 접속 알림 메시지
            val userRef : DatabaseReference = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
            userRef.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for ( user in snapshot.children){
                        if ( user.child("nickName").value.toString() == userInfo.nickName){
                            val tmpUser : UserModel = UserModel(user.child("userKey").value.toString(), user.child("email").value.toString(), user.child("nickName").value.toString(), my_page_userContentEdit.text.toString(), user.child("dateTime").value.toString() )
                            val userValues = tmpUser.toMap()
                            val userUpdates = hashMapOf<String, Any>(
                                "/${user.child("userKey").value.toString()}" to userValues
                            )
                            userRef.updateChildren(userUpdates)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
//                .also { val main_Intent = Intent(requireContext(), MainActivity::class.java)
//                main_Intent.putExtra("email", userInfo.email)
//                main_Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                main_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                startActivity(main_Intent)
//            }

        }
    }
}