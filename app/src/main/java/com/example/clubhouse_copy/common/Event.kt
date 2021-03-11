package com.example.clubhouse_copy.common

//T type의 데이터 읽기만 하기
open class Event<out T>(private val content : T){

    var hasBeenHandled = false

    fun getContentIfNotHandled() : T?{
        return if(hasBeenHandled){
            null
        }else {
            hasBeenHandled = true
            content
        }
    }

    fun get(action: (T) ->Unit){
        getContentIfNotHandled()?.let(action)
    }

    fun peekContent() : T = content

}