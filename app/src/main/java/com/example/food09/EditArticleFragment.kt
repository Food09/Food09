package com.example.food09

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf


class EditArticleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_edit_article, container, false)
        val btnSubmit : Button = rootView.findViewById(R.id.buttonSubmit_edit)

        btnSubmit.setOnClickListener { view->
            Log.d("EditArticleFragment", "Submit Clicked!")
            val result = "result"
//            setFragmentResult
            parentFragmentManager.setFragmentResult("requestKey", bundleOf("data" to result))
            parentFragmentManager.popBackStack()
        }
        return rootView
    }

}