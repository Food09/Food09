package com.example.project

import com.google.firebase.database.Exclude
import java.io.Serializable

class ArticleModel (
    var articleNum: Int,
    val userID: String,
    val userProfile: String,
    var category: String,
    var title: String,
    var content: String,
    var maxNum: Int,
    var curNum: Int
    ) : Serializable{
    fun get_articleNum(): Int? {
        return articleNum
    }
    fun get_userID(): String? {
        return userID
    }
    fun get_userProfile(): String? {
        return userProfile
    }
    fun get_title(): String? {
        return title
    }
    fun get_category(): String? {
        return category
    }
    fun get_content(): String? {
        return content
    }
    fun get_maxNum(): Int?{
        return maxNum
    }
    fun get_curNum(): Int?{
        return curNum
    }
    fun set_curNum(curNum: Int){
        this.curNum = curNum
    }
    fun set_maxNum(maxNum: Int){
        this.maxNum = maxNum
    }
    fun set_title(title: String){
        this.title = title
    }
    fun set_content(content: String){
        this.content = content
    }
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
//            "articleNum" to articleNum, // articleNum is the key of article
            "userID" to userID,
            "userProfile" to userProfile,
            "category" to category,
            "title" to title,
            "content" to content,
            "maxNum" to maxNum,
            "curNum" to curNum
        )
    }
}

