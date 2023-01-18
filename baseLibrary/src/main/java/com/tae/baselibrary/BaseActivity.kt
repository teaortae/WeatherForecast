package com.tae.baselibrary


import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tae.baselibrary.ui.CustomSnackBar
import com.tae.baselibrary.viewmodel.BaseViewModel

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel>(@LayoutRes val layoutId: Int) :
    AppCompatActivity() {

    abstract fun initData()
    abstract fun initView()
    abstract fun eventObservers()
    lateinit var binding: T
    abstract val viewModel: R


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        binding.setVariable(layoutId, viewModel)
        binding.executePendingBindings()
        initData()
        initView()
        eventObservers()
    }


    /**
     * toast one time
     * @param[msg] message
     **/
    fun showToast(msg: String) {
        CustomSnackBar.showToast(msg, this)
    }
}