package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ArticleFragment : Fragment() {

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
            val article : ArticleModel = requireArguments()!!.getSerializable("articleInfo") as ArticleModel
            Log.d("ArticleFragment", "ArticleInfo : " + article.get_userID())
            userID.text = article.get_userID()
            userProfile.text = article.get_userProfile()
            title.text = article.get_title()
            content.text = article.get_content()
            userNumber.text = article.get_curNum().toString() + " / " + article.get_maxNum().toString()
        }

        return rootView
    }


}