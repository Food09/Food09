package com.example.project

import com.google.firebase.database.Exclude
import java.io.Serializable

class ChatModel (
    var chatKey : String,
    var nickName : String,
    var content : String,
    var dateTime : String
    ) : Serializable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "chatKey" to chatKey,
            "nickName" to nickName,
            "content" to content,
            "dateTime" to dateTime
        )
    }
}