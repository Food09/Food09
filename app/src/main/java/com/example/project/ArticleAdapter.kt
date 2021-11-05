package com.example.project

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ArticleAdapter(private val DataList: ArrayList<ArticleModel>, val itemClick: (ArticleModel) -> Unit): RecyclerView.Adapter<ArticleAdapter.ViewHolder> () {

    var articleDataList = ArrayList<ArticleModel>()
    val storage : FirebaseStorage = Firebase.storage("gs://food09-581c6.appspot.com/")
    val imageRef : StorageReference = storage.getReference("Images")

    inner class ViewHolder(itemView: View?, itemClick: (ArticleModel) -> Unit): RecyclerView.ViewHolder(itemView!!){

        private val txtTitle: TextView = itemView!!.findViewById(R.id.titleTextView)
        private val txtContent: TextView = itemView!!.findViewById(R.id.contentTextView)
        private val txtNum: TextView = itemView!!.findViewById(R.id.numTextView)
        private val imgThumbnail : ImageView = itemView!!.findViewById(R.id.thumbnailImageView)

        fun bind(data: ArticleModel, position: Int){
            txtTitle.text = data.get_title()
            txtContent.text = data.get_content()

            val curNum = data.get_curNum()
            val maxNum = data.get_maxNum()
            val numStr = curNum.toString() + " / " + maxNum.toString()
            txtNum.text = numStr

            Log.d("ArticleAdapter imageUrl test", data.imageUrls.toString())

            val tmpImageRef : StorageReference = imageRef.child(data.imageUrls[0])
            tmpImageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    Glide.with(imgThumbnail.context).load(it.result).into(imgThumbnail)
                }
            }

            itemView.setOnClickListener {
                itemClick(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
//        Log.d("ArticleAdapter", "getItemCount : " + articleDataList.count())
        return articleDataList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.d("ArticleAdapter", "===== ===== ===== ===== onBindViewHolder ===== ===== ===== =====")
        holder.bind(articleDataList[position], position)
    }

    fun replaceList(newList: ArrayList<ArticleModel>){
        articleDataList = newList
        notifyDataSetChanged()
    }
}