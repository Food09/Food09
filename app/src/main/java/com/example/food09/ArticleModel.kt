package com.example.food09

import java.io.Serializable

class ArticleModel (
    var ArticleNum: Int,
    val userID: String,
    val userProfile: String,
    var category: String,
    var title: String,
    var content: String,
    var maxNum: Int,
    var curNum: Int
    ) : Serializable{
    fun get_articleNum(): Int? {
        return ArticleNum
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
}

