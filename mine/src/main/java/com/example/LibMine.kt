package com.example

import com.example.mine.ui.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//我的模块的相关koin的module
val moduleMine= module {
    viewModel {
        MineViewModel()
    }
}