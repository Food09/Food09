package com.example.food09

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(private val testDataList: ArrayList<TestModel>): RecyclerView.Adapter<TestAdapter.ViewHolder> () {

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!){ // null이 아님

        var data1: TextView = itemView!!.findViewById(R.id.test_titleTextView)
        var data2: TextView = itemView!!.findViewById(R.id.test_contentTextView)

        fun bind(data: TestModel, position: Int){
            Log.d("TestAdapter", "===== ===== ===== ===== bind ===== ===== ===== =====")
            Log.d("TestAdapter", data.getData1()+" "+data.getData2())
            data1.text = data.getData1()
            data2.text = data.getData2()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("TestAdapter", "getItemCount : " + testDataList.count())
        return testDataList.count()
    }

    override fun onBindViewHolder(holder: TestAdapter.ViewHolder, position: Int) {
        Log.d("TestAdapter", "===== ===== ===== ===== onBindViewHolder ===== ===== ===== =====")
        holder.bind(testDataList[position], position)
    }
}