package com.example.clubhouse_copy.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BaseViewModel : ViewModel() {

    private val _liveLoading = MutableLiveData(false)

    val liveLoading : LiveData<Boolean>
    get() = _liveLoading


    fun showLoading(){
        _liveLoading.value = true
    }

    fun hideLoading(){
        _liveLoading.value =false
    }


}