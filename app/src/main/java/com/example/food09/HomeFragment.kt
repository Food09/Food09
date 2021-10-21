package com.example.food09

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food09.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    var articleDataList = ArrayList<ArticleModel>()
    val myAdapter = ArticleAdapter(articleDataList)

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

    fun articleDummy(){
        articleDataList = arrayListOf<ArticleModel>(
            ArticleModel("charli1", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli2", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli3", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli4", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli5", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli6", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli7", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli8", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli9", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli10", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            ArticleModel("charli11", "FastFood", "밥 먹을사람 구함!", "같이 햄버거 먹을 사람 구해요", 5, 1),
            )
        Log.d("HomeFragment", "Test Data List are made!")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView!!) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(ArticleItemDecorator(10))

        //testDummy()
        articleDummy()
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