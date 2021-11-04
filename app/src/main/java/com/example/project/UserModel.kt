package com.example.project

import com.google.firebase.database.Exclude
import java.io.Serializable

class UserModel (
    var userKey : String,
    var email : String,
    var nickName : String,
    var profile : String,
    var dateTime : String
    ) : Serializable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userKey" to userKey,
            "email" to email,
            "nickName" to nickName,
            "profile" to profile,
            "dateTime" to dateTime
        )
    }
}