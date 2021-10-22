package com.example.food09

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food09.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    var articleDataList = ArrayList<ArticleModel>()
    lateinit var myAdapter : ArticleAdapter
//    var activity : MainActivity? = null  // 사용안함

    fun testDummy(){
        var testDataList : ArrayList<TestModel> = arrayListOf<TestModel>(
            TestModel("Title1", "Content1"),
            TestModel("Title2", "Content2"),
            TestModel("Title3", "Content3"),
            TestModel("Title4", "Content4"),
            TestModel("Title5", "Content5"),
            TestModel("Title6", "Content6"),
            TestModel("Title7", "Content7"),
            TestModel("Title8", "Content8"),
            TestModel("Title9", "Content9"),
        )
        Log.d("HomeFragment", "Test Data List are made!")
        recyclerView.adapter = TestAdapter(testDataList)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        activity = getActivity() as MainActivity
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        activity = null
//    }

    fun articleDummy(){
        articleDataList = arrayListOf<ArticleModel>(
            ArticleModel(1,"charli1", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "12345678901234567", "123456789012345678901234567890123456789012345678901234567890", 5, 3),
            ArticleModel(2,"charli2", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "곱창 같이 드실 분 구해요!", "야곱야곱에서 야채곱창 시키려고 하는데, 탑승하실 분 있으면 들어와주세요~", 5, 2),
            ArticleModel(3,"charli3", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 4, 1),
            ArticleModel(4,"charli4", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 6, 1),
            ArticleModel(5,"charli5", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 3),
            ArticleModel(6,"charli6", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 6, 1),
            ArticleModel(7,"charli7", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel(8,"charli8", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 7, 4),
            ArticleModel(9,"charli9", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel(10,"charli10", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel(11,"charli11", "저는 미래관에 사는 야채곱창을 좋아하는 화석입니다.","FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            )
        Log.d("HomeFragment", "Test Data List are made!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", "onCreat called!")
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

        //testDummy()
        articleDummy()
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
        myAdapter.replaceList(articleDataList)
        recyclerView.adapter = myAdapter

        return rootView
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }


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
                transaction.add(R.id.fragementContainer, editArticleFragment).addToBackStack(null).commit()
            }
        }


    }


}