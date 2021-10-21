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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView!!) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        binding.recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//        binding.recyclerView.adapter = testAdapter
//        testAdapter.notifyDataSetChanged()

        testDummy()

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