package com.example.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.FragmentHomeBinding
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    var articleDataList = ArrayList<ArticleModel>()
    lateinit var myAdapter : ArticleAdapter
    lateinit var articleRef : DatabaseReference
    var flag : Int = 0
//    var activity : MainActivity? = null  // 사용안함


//    fun testDummy(){
//        var testDataList : ArrayList<TestModel> = arrayListOf<TestModel>(
//            TestModel("Title1", "Content1"),
//            TestModel("Title2", "Content2"),
//            TestModel("Title3", "Content3"),
//            TestModel("Title4", "Content4"),
//            TestModel("Title5", "Content5"),
//            TestModel("Title6", "Content6"),
//            TestModel("Title7", "Content7"),
//            TestModel("Title8", "Content8"),
//            TestModel("Title9", "Content9"),
//        )
//        Log.d("HomeFragment", "Test Data List are made!")
//        recyclerView.adapter = TestAdapter(testDataList)
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        activity = getActivity() as MainActivity
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        activity = null
//    }

//    fun articleDummy(){
//        articleDataList = arrayListOf<ArticleModel>(
//            ArticleModel("1","charli1", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "12345678901234567", "123456789012345678901234567890123456789012345678901234567890", 5, 3, "2019-03-23T00:05:54.608"),
//            ArticleModel("2","charli2", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "곱창 같이 드실 분 구해요!", "야곱야곱에서 야채곱창 시키려고 하는데, 탑승하실 분 있으면 들어와주세요~", 5, 2, "2019-03-23T00:05:54.608"),
//            ArticleModel("3","charli3", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 4, 1, "2019-03-23T00:05:54.608"),
//            ArticleModel("4","charli4", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 6, 1, "2019-03-23T00:05:54.608"),
//            ArticleModel("5","charli5", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 3, "2019-03-23T00:05:54.608"),
//            ArticleModel("6","charli6", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 6, 1, "2019-03-23T00:05:54.608"),
//            ArticleModel("7","charli7", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1, "2019-03-23T00:05:54.608"),
//            ArticleModel("8","charli8", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 7, 4, "2019-03-23T00:05:54.608"),
//            ArticleModel("9","charli9", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1, "2019-03-23T00:05:54.608"),
//            ArticleModel("10","charli10", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1, "2019-03-23T00:05:54.608"),
//            ArticleModel("11","charli11", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1, "2019-03-23T00:05:54.608"),
//            )
//        Log.d("HomeFragment", "Test Data List are made!")
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", "onCreat called!")

        // EditArticle로부터 article을 받아오기
        requireFragmentManager().setFragmentResultListener("requestKey", this) { key, bundle ->
//            val result = bundle.getString("data")
            val article : ArticleModel = bundle.getSerializable("articleInfo") as ArticleModel
            Log.d("HomeFragment", "Receive data from EditArticleFragment : " + article.get_userID())
            // EditArticle로부터 받아온 article을 리스트에 추가하기
            // ToDo: local에 쓰는 것 없이 firebase에 추가
            val uploadRef : DatabaseReference = articleRef.push()
            val key : String? = uploadRef.key
            if (key == null) {
                return@setFragmentResultListener
            }
            article.set_articleKey(key) // articleKey 세팅
            Log.d("HomeFragment article! : ", article.toString())
            val articleValues = article.toMap()

            val childUpdates = hashMapOf<String, Any>(
                "/${article.get_articleKey()}" to articleValues
            )
            articleRef.updateChildren(childUpdates)
            readArticle()
//            articleDataList.add(article)
//            myAdapter.replaceList(articleDataList)
//
        }
    }

    fun dbInit(){
        val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/")
        articleRef = database.getReference("Article")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("log", "onCreateView! - home")
        var rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView!!) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(ArticleItemDecorator(10))

        if (flag == 0){
            dbInit()
            //testDummy()
            //articleDummy()

            // recycleView itemCLickListener
            myAdapter = ArticleAdapter(articleDataList) { article ->
                Log.d("ItemClickListener", "data :" + article.get_userID())
                // HomeFragment에서 ArticleFragment로 이동
                val bundle: Bundle = Bundle()
//           bundle.putString("test", "tttt")
                bundle.putSerializable("articleInfo", article)
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                val articleFragment = ArticleFragment()
                articleFragment.setArguments(bundle)
//            transaction.replace(R.id.fragementContainer, articleFragment).commit()
                transaction.add(R.id.fragementContainer, articleFragment).addToBackStack(null).commit()
            }


            flag = 1
        }




        readArticle()
//        myAdapter.replaceList(articleDataList)
        recyclerView.adapter = myAdapter

//        test()

        return rootView
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun readArticle(){
        articleRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var tmpArticleDataList = ArrayList<ArticleModel>()
                for ( article in snapshot.children ){
                    Log.d("Firebase Test", article.key.toString())
                    val articleKey : String = article.key.toString()
                    val title : String = article.child("title").value.toString()
                    val content : String = article.child("content").value.toString()
                    val userID : String = article.child("userID").value.toString()
                    val userProfile : String = article.child("userProfile").value.toString()
                    val category : String = article.child("category").value.toString()
                    val curNum : Int = article.child("curNum").value.toString().toInt()
                    val maxNum : Int = article.child("maxNum").value.toString().toInt()
                    val dateTime : String = article.child("dateTime").value.toString()
                    val members : ArrayList<String> = arrayListOf(userID)
                    tmpArticleDataList.add(ArticleModel(articleKey, userID, userProfile, category, title, content, maxNum, curNum, dateTime, members))
                }
                articleDataList = tmpArticleDataList
                myAdapter.replaceList(articleDataList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase Test", "Failed to read Database")
            }
        })
    }

//    fun test(){
//        // example of Read/Write Data to firebase
//
//        val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://food09-581c6-default-rtdb.asia-southeast1.firebasedatabase.app/")
//        val myRef : DatabaseReference = database.getReference("Article")
//
//        // Modify Data
//        myRef.child("2").child("title").setValue("zzzzzzzz")
//
//        // Read Data
//        myRef.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("Firebase Test", snapshot.value.toString())
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("Firebase Test", "Failed to read Database")
//            }
//        })
//        Log.d("Firebase Test", myRef.get().toString())
//
//        // Add Data
//        val key = myRef.push().push().key
//        if (key == null) {
//            Log.w("Firebase Test ", "Couldn't get push key for posts")
//            return
//        }
//
//        Log.d("Firebase Test Key : ", key.toString())
//        val article : ArticleModel = ArticleModel("sd","charli7", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!1111111111111", "같이 햄버거 먹을 사람 구해요", 5, 1, "2019-03-23T00:05:54.608")
//        val articleValues = article.toMap()
//
//        val childUpdates = hashMapOf<String, Any>(
//            "/${article.get_articleKey()}" to articleValues
//        )
//        myRef.updateChildren(childUpdates)
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("log", "onViewCreated! - home")


        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        var binding: FragmentHomeBinding? = null

        binding = fragmentHomeBinding


        // Floating Button Action
        binding.addFloatingButton.setOnClickListener {
            context.let {
                Log.d("log", "Floating Button pushed!")
                val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                val editArticleFragment = EditArticleFragment()
                // ToDo: user 정보 bundle 추가해야함
                transaction.add(R.id.fragementContainer, editArticleFragment).addToBackStack(null).commit()
            }
        }


    }


}