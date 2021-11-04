package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class MyPageFragment : Fragment() {

    private lateinit var userInfo : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MainActivity로부터 사용자 정보 받아오기
        if (getArguments() == null){
            return
        }
        userInfo = requireArguments()!!.getSerializable("userInfo") as UserModel
        Log.d("MyPageFragment getArguments : ", userInfo.nickName)
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

}