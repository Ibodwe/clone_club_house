package com.example.clubhouse_copy.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class EventLiveData<T> : MutableLiveData<Event<T>>() {

    operator fun plusAssign(value : T){
        setEventValue(value)
    }

    fun setEventValue(value : T){
        // super는 상속받는 class 자체를 가르 킴
        super.setValue(Event(value))
    }

    fun observe(owner : LifecycleOwner , onResult :(T) -> Unit){
        super.observe(owner, Observer {
            it.get(onResult)
        })
    }


}