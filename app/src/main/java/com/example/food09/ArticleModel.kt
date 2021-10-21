package com.example.food09

class ArticleModel (
    val userID: String,
    var category: String,
    var title: String,
    var text: String,
    var maxNum: Int,
    var curNum: Int
    ){
    fun get_userID(): String? {
        return userID
    }
    fun get_title(): String? {
        return title
    }
    fun get_category(): String? {
        return category
    }
    fun get_text(): String? {
        return text
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

