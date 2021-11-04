package com.example.project

import com.google.firebase.database.Exclude
import java.io.Serializable
import java.util.function.Predicate


class ArticleModel (
    var articleKey: String,  // 채팅방 키로 사용함
    val userID: String,
    val userProfile: String,
    var category: String,
    var title: String,
    var content: String,
    var maxNum: Int,
    var curNum: Int,
    var dateTime: String?,
    var members: ArrayList<String>,
    var imageUrls: ArrayList<String>
    ) : Serializable{
    fun get_imageUrls() : ArrayList<String> {
        return imageUrls
    }
    fun add_imageUrl(imageUrl: String) {
        this.imageUrls.add(imageUrl)
    }
    fun del_imageUrl(imageUrl: String) {
        this.imageUrls.remove(imageUrl)
    }
    fun get_articleKey(): String? {
        return articleKey
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
    fun get_dateTime(): String?{
        return dateTime
    }
    fun get_members(): ArrayList<String>?{
        return members
    }
    fun add_members(userID: String) {
        this.members.add(userID)
    }
    fun del_members(userID: String) {
        members.remove(userID)
    }
    fun set_articleKey(articleKey: String){
        this.articleKey = articleKey
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
            "articleKey" to articleKey, // articleKey is the key of article
            "userID" to userID,
            "userProfile" to userProfile,
            "category" to category,
            "title" to title,
            "content" to content,
            "maxNum" to maxNum,
            "curNum" to curNum,
            "dateTime" to dateTime,
            "members" to members
        )
    }
}

