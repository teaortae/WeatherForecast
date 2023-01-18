package com.tae.baselibrary.modules

import com.tae.baselibrary.viewmodel.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object BaseViewModelModule {
    val module = module {
        viewModel { BaseViewModel() }
    }
}
