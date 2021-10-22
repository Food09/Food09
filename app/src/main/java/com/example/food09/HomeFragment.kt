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
    var activity : MainActivity? = null

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    fun articleDummy(){
        articleDataList = arrayListOf<ArticleModel>(
            ArticleModel("charli1", "FastFood", "12345678901234567", "123456789012345678901234567890123456789012345678901234567890", 5, 3),
            ArticleModel("charli2", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 2),
            ArticleModel("charli3", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 4, 1),
            ArticleModel("charli4", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 6, 1),
            ArticleModel("charli5", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 3),
            ArticleModel("charli6", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 6, 1),
            ArticleModel("charli7", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli8", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 7, 4),
            ArticleModel("charli9", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli10", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli11", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
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
            bundle.putString("test", "test")
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            val articleFragment = ArticleFragment()
            transaction.replace(R.id.fragementContainer, articleFragment).commit()

            // ToDo: Child Fragment로 ArticleFragment를 생성하고 데이터를 전달해야함

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
            }
        }


    }


}