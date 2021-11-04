package com.example.project

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class EditArticleFragment : Fragment() {
    private lateinit var userInfo : UserModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_edit_article, container, false)
        val btnSubmit : Button = rootView.findViewById(R.id.buttonSubmit_edit)

        val userID : TextView = rootView!!.findViewById(R.id.userIDTextView_edit)
        val userProfile : TextView = rootView!!.findViewById(R.id.userProfileTextView_edit)
        val title : EditText = rootView!!.findViewById(R.id.titleTextView_edit)
        val content : EditText = rootView!!.findViewById(R.id.contentTextView_edit)
        val userNumber : TextView = rootView!!.findViewById(R.id.userNumberTextView_edit)


        // HomeFragment로부터 사용자 정보 받아오기
        if (getArguments() == null){
            Log.d("EditArticleFragment getArguments : ", "getArguments Error!")
        }
        userInfo = requireArguments()!!.getSerializable("userInfo") as UserModel
        Log.d("HomeFragment getArguments : ", userInfo.nickName)

        userID.text = userInfo.nickName
        userProfile.text = userInfo.profile

        // ToDo: 최대 인원 수 지정을 위한 Spinner


        btnSubmit.setOnClickListener { view->
            if ( TextUtils.isEmpty(title.text.toString().trim()) || TextUtils.isEmpty(content.text.toString().trim()) ){
                Toast.makeText(context, "글을 모두 작성해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("EditArticleFragment", "Submit Clicked!")
            val now = System.currentTimeMillis()
            val dateTime : String? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)

            Log.d("EditArticleFragment", "LocalDateTime : " + dateTime)
            val members : ArrayList<String> = arrayListOf(userInfo.email)
            val imageUrls : ArrayList<String> = arrayListOf("gs://food09-581c6.appspot.com/도시락.jpg")
            var article : ArticleModel = ArticleModel("None", userInfo.nickName, userInfo.profile, "fastfood", "title", "content", 5, 1, dateTime, members, imageUrls)
            article.set_title(title.text.toString())
            article.set_content(content.text.toString())
            val bundle: Bundle = Bundle()
            bundle.putSerializable("articleInfo", article)
            parentFragmentManager.setFragmentResult("requestKey", bundle)
            parentFragmentManager.popBackStack()
        }
        return rootView
    }

}