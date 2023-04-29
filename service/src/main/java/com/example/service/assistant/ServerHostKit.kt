package com.example.service.assistant

import android.content.Context
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.example.service.R

class ServerHostKit:AbstractKit() {
    //调式工具显示的名字和图标
    override val icon: Int
        get() = R.drawable.icon_server_host
    override val name: Int
        get() = R.string.str_server_host_dokit

    //初始化
    override fun onAppInit(context: Context?) {

    }

    //点击事件
    override fun onClick(context: Context?) {

    }
}