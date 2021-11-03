package com.example.project

import java.io.Serializable

class ChatModel (
    var chatKey : String,
    var nickName : String,
    var content : String,
    var dateTime : String
    ) : Serializable {
}