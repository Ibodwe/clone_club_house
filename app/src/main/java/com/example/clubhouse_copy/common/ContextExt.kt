package com.example.clubhouse_copy.common

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



    fun AppCompatActivity.showMessage(message : String, isLong : Boolean = false){
        Toast.makeText(applicationContext, message , if(isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

