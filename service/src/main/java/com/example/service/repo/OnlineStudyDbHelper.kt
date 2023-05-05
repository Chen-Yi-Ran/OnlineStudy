package com.example.service.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object OnlineStudyDbHelper {

     var  id :Int = 0

    /*
    * 获取room数据表中存储的userInfo
    * return liveData观察者形式
    * liveData的形式不用单独声明到一个线程*/
    fun getLiveUserInfo(context: Context): LiveData<UserInfo> {
        LogUtils.d(id)
      return  Online_Study_Database.getInstance(context).userDao.queryLiveUser(148145)
    }

    /*
    * 以普通数据对象的形式，获取userInfo
    * */
    fun getUserInfo(context: Context) :UserInfo=
            Online_Study_Database.getInstance(context).userDao.queryUser(148145)



    /*
    * 删除数据表中的userInfo信息
    * */
    fun deleteUserInfo(context: Context) {
//        val currentUser= getUserInfo(context)?:return
//        online_study_Database.getInstance(context).userDao.deleteUser(currentUser)
         // LogUtils.d( getUserInfo(context))
        GlobalScope.launch {

            getUserInfo(context)?.let { info ->
                Online_Study_Database.getInstance(context).userDao.deleteUser(info)
            }
            }
        }




    /**
     * 新增用户数据到数据表
     */
    fun insertUserInfo(context: Context,userInfo: UserInfo){

        LogUtils.d(Online_Study_Database.getInstance(context))
        //GlobalScope.launch和应用周期保持一致，当应用程序结束也会一起结束
        GlobalScope.launch(Dispatchers.IO) {
            Online_Study_Database.getInstance(context).userDao.insertUser(userInfo)
            id=userInfo.data.id

        }
    }


}

