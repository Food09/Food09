package com.example.project

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class EditArticleFragment : Fragment() {
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

        // ToDo: User 정보 받아서 TextView에 넣고 전달할 Article에 추가해야함

        btnSubmit.setOnClickListener { view->
            // ToDo: check field

            Log.d("EditArticleFragment", "Submit Clicked!")
            val now = System.currentTimeMillis()
            val dateTime : String? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)

//            val dateTime : String = LocalDateTime.now().toString()
            Log.d("EditArticleFragment", "LocalDateTime : " + dateTime)
            val members : ArrayList<String> = arrayListOf("charlie")
            var article : ArticleModel = ArticleModel("1", "charlie", "asdf", "fastfood", "title", "content", 5, 1, dateTime, members)
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