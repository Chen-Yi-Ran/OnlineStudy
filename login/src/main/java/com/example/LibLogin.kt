package com.example

import com.example.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * koin注解管理类
 */
val moduleLogin:Module= module {

    viewModel {
        LoginViewModel()
    }
}