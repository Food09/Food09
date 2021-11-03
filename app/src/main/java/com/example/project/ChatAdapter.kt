package com.example.project

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val currentUserID: String, private val DataList: ArrayList<ChatModel>) : RecyclerView.Adapter<ChatAdapter.ViewHolder> () {


    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){

        val card : CardView = itemView!!.findViewById(R.id.cardView_chat)
        val nickName : TextView = itemView!!.findViewById(R.id.nicknameTextView_chat)
        val dateTime : TextView = itemView!!.findViewById(R.id.datetimeTextView_chat)
        val content : TextView = itemView!!.findViewById(R.id.contentTextView_chat)


        fun bind(data: ChatModel, position: Int){

            if (currentUserID == DataList[position].nickName) {
                card.setCardBackgroundColor(Color.parseColor("#FFF176"))
            }

            nickName.text = DataList[position].nickName
            dateTime.text = DataList[position].dateTime
            content.text = DataList[position].content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return DataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(DataList[position], position)
    }

//    fun replaceList(newList: ArrayList<ChatModel>){
//
//    }

}