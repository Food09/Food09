package com.example.project

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_edit_article.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class EditArticleFragment : Fragment() {
    val storage : FirebaseStorage = Firebase.storage("gs://food09-581c6.appspot.com/")
    val imageRef : StorageReference = storage.getReference("Images")

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
        val categorySpinner : Spinner = rootView!!.findViewById(R.id.Category_spinner)
        val categoryImageView : ImageView = rootView!!.findViewById(R.id.edit_article_imageView)

        var categoryValue : String ?= null
        var categoryImageUrl : String ?= null
        var articleKey : String = "None"
        var userInfo : UserModel


        // HomeFragment로부터 사용자 정보 받아오기
        if (requireArguments().containsKey("userInfo")){
            userInfo = requireArguments()!!.getSerializable("userInfo") as UserModel
            Log.d("HomeFragment getArguments : ", userInfo.nickName)

            userID.text = userInfo.nickName
            userProfile.text = userInfo.profile
        } else {
            Log.w("EditArticleFragment getArguments : ", "getArguments Error!")
        }

        // 최대 인원 수 지정을 위한 Spinner -> 보류

        // 카테고리 지정을 위한 Spinner -> 추가적인 사진 업로드가 없을시 기본 사진 경로 지정해줘야함
        // Spinner Adapter 등록
        val res : Resources = resources
        val categoryList = arrayListOf<String>(*res.getStringArray(R.array.food_category))
        val categoryImageUrlList = arrayListOf<String>(*res.getStringArray(R.array.food_default_iamge_category))
//        Log.d("categoryList", categoryList.toString())
        val categoryAdapter : ArrayAdapter<String> = ArrayAdapter<String>(requireActivity().baseContext, android.R.layout.simple_spinner_item, categoryList)
        categorySpinner.adapter = categoryAdapter
        // Spinner Listenr 등록
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {
                        categoryValue = categoryList[0]
                        categoryImageUrl = categoryImageUrlList[0]
                    }
                    1 -> {
                        categoryValue = categoryList[1]
                        categoryImageUrl = categoryImageUrlList[1]
                    }
                    2 -> {
                        categoryValue = categoryList[2]
                        categoryImageUrl = categoryImageUrlList[2]
                    }
                    3 -> {
                        categoryValue = categoryList[3]
                        categoryImageUrl = categoryImageUrlList[3]
                    }
                    4 -> {
                        categoryValue = categoryList[4]
                        categoryImageUrl = categoryImageUrlList[4]
                    }
                    5 -> {
                        categoryValue = categoryList[5]
                        categoryImageUrl = categoryImageUrlList[5]
                    }
                    6 -> {
                        categoryValue = categoryList[6]
                        categoryImageUrl = categoryImageUrlList[6]
                    }
                    7 -> {
                        categoryValue = categoryList[7]
                        categoryImageUrl = categoryImageUrlList[7]
                    }
                    8 -> {
                        categoryValue = categoryList[8]
                        categoryImageUrl = categoryImageUrlList[8]
                    }
                    9 -> {
                        categoryValue = categoryList[9]
                        categoryImageUrl = categoryImageUrlList[9]
                    }
                    10 -> {
                        categoryValue = categoryList[10]
                        categoryImageUrl = categoryImageUrlList[10]
                    }
                    11 -> {
                        categoryValue = categoryList[11]
                        categoryImageUrl = categoryImageUrlList[11]
                    }
                }
                val tmpImageRef : StorageReference = imageRef.child(categoryImageUrl.toString())
                tmpImageRef.downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Glide.with(categoryImageView.context).load(it.result).centerCrop().placeholder(R.drawable.ic_baseline_fastfood_24).into(categoryImageView)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                categoryValue = null
                categoryImageUrl = null
            }
        }

        if (requireArguments().containsKey("articleInfo")){
            Log.d("EditArticleFragment", "AritlceFragment에서 수정요청")
            val tmpArticle : ArticleModel = requireArguments()!!.getSerializable("articleInfo") as ArticleModel
            title.setText(tmpArticle.title)
            content.setText(tmpArticle.content)
            categoryValue = tmpArticle.category
            categoryImageUrl = tmpArticle.imageUrls[0]
            articleKey = tmpArticle.articleKey
        }

        btnSubmit.setOnClickListener { view->
            if ( TextUtils.isEmpty(title.text.toString().trim()) || TextUtils.isEmpty(content.text.toString().trim()) ){
                Toast.makeText(context, "글을 모두 작성해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if ( categoryValue == null ){
                Toast.makeText(context, "음식 카테고리를 선택해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ArticleModel 생성 후 HomeFragment로 전달
            Log.d("EditArticleFragment", "Submit Clicked!")
            val now = System.currentTimeMillis()
            val dateTime : String? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN).format(now)
            Log.d("EditArticleFragment", "LocalDateTime : " + dateTime)

            // ToDo: members
            val members : ArrayList<String> = arrayListOf(userID.text.toString())
            val imageUrls : ArrayList<String> = arrayListOf(categoryImageUrl.toString())
            // ToDo: articleKey, category
            var article : ArticleModel = ArticleModel(articleKey, userID.text.toString(), userProfile.text.toString(), categoryValue.toString(), title.text.toString(), content.text.toString(), 5, 1, dateTime, members, imageUrls)
//            article.set_title(title.text.toString())
//            article.set_content(content.text.toString())
            val bundle: Bundle = Bundle()
            bundle.putSerializable("articleInfo", article)
            parentFragmentManager.setFragmentResult("requestKey", bundle)
            parentFragmentManager.popBackStack()
        }
        return rootView
    }

}