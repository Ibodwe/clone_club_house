package com.example.clubhouse_copy.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
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


        binding {
            lifecycleOwner = this@BaseActivity
            setVariable(5 , viewModel)
        }

        viewModel{
            liveLoading.observe(this@BaseActivity) {
                this@BaseActivity.showMessage(it.toString())
            }
        }


    }

    // action이 함수다 --> 함수 run으로 실행
    protected fun binding(action: B.() -> Unit){
        binding.run(action)
    }

    protected fun viewModel(action : VM.() -> Unit){
        viewModel.run(action)
    }

    //infix함수는, 객체와 객체 사이를 연결 해주는 역할을 한다.
    protected infix fun <T> LiveData<T>.observe(action : (T) -> Unit){
        observe(this@BaseActivity, action)
    }

    protected infix fun <T> LiveData<Event<T>>.eventObserve(action: (T) -> Unit){
        observe(this@BaseActivity, {it.get(action)})
    }



}