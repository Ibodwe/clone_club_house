package com.example.clubhouse_copy.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.viewmodel.compose.viewModel
import java.lang.reflect.ParameterizedType

abstract class BaseActivity< B : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutResId:Int): AppCompatActivity() {

    protected lateinit var binding : B

    private val viewModelClass = ((javaClass.genericSuperclass as ParameterizedType?)
        ?.actualTypeArguments
        ?.get(1) as Class<VM>).kotlin

    protected open val viewModel by ViewModelLazy(
        viewModelClass,
        {viewModelStore},
        {defaultViewModelProviderFactory}
    )

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = DataBindingUtil.setContentView(this , layoutResId)


        binding.apply {
            lifecycleOwner = this@BaseActivity
            setVariable(5 , viewModel)
        }

        viewModel.apply{
            liveLoading.observe(this@BaseActivity) {
                this@BaseActivity.showMessage(it.toString())
            }
        }
        
    }


}