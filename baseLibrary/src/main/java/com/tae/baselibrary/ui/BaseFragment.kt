package com.tae.baselibrary.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tae.baselibrary.isOnline
import com.tae.baselibrary.viewmodel.BaseViewModel

abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel>
    (@LayoutRes private val layoutId: Int) : Fragment() {

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initView()
    abstract fun eventObservers()
    private var _binding: T? = null
    val binding: T get() = _binding !!
    var needRefresh = false
    lateinit var backPressedCallback: OnBackPressedCallback

    abstract val viewModel: R

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //back button event
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            backPressedCallback
        )
    }

    override fun onDetach() {
        super.onDetach()
        //remove back button event
        backPressedCallback.remove()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(!requireContext().isOnline){
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("")
                .setMessage("인터넷 연결을 확인 해 주세요.")
                .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                    requireActivity().finish()
                }
                .setCancelable(false)
                .show()
        }
//        if (_binding == null) {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(layoutId, viewModel)
        binding.executePendingBindings()
        initData(savedInstanceState)
        initView()
//        }
        eventObservers()
        return binding.root
    }

    /**
     * toast one time
     * @param[msg] message
     **/
    fun showToast(msg: String) {
        CustomSnackBar.showToast(msg, requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.lifecycleOwner = null
        _binding = null
    }

     abstract fun onBackPressed()
}