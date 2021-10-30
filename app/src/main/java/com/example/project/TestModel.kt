package com.example.project

class TestModel(
    private var data1: String? = null,
    private var data2: String? = null,
){
    fun getData1(): String? {
        return data1
    }
    fun getData2(): String? {
        return data2
    }
}