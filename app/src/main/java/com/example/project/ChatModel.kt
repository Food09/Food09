package com.example.project

import java.io.Serializable

class ChatModel (
    var nickName : String,
    var content : String,
    var dateTime : String
    ) : Serializable {
}