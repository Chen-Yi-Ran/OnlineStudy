package com.example.service.assistant

import android.app.AlertDialog
import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.cniao5.common.utils.*
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

    private val hostMap= mapOf<String,String>(
        "开发环境Host" to HOST_DEV,
        "QA环境Host" to HOST_QA,
        "线上正式Host" to HOST_PRODUCT
    )

    private val hosts=hostMap.values.toTypedArray()
    private val names=hostMap.keys.toTypedArray()

    //点击事件
    override fun onClick(context: Context?) {
        val pos=hostMap.values.indexOf(getBaseHost())%hostMap.size
        //弹窗,用于显示不同的host配置环境
        context?:return ToastUtils.showShort("~~ context null ~~")
        AlertDialog.Builder(context)
            .setTitle("切换Host")
            .setSingleChoiceItems(names,pos){
                dialog,which->
                setBaseHost(hosts[which])
                dialog.dismiss()
            }.show()


    }
}