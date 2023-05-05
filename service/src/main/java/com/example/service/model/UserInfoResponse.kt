package com.example.service.model

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

//mine模块用户信息的返回数据，api的data的解析后的数据类型


@Parcelize
 data class UserInfoResponse (
    val `data`: @RawValue   Data?,
    val errorCode: Int?,
    val errorMsg: String?
):Parcelable

data class Data(
    @Embedded
    val coinInfo: CoinInfo,
    @Embedded
    val collectArticleInfo:  CollectArticleInfo,
    @Embedded
    val userInfo: UserInfo
)

data class CoinInfo(
    val coinCount: Int?,
    val level: Int,
    val nickname: String,
    val rank: Int?,
    val userId: Int,
    val username: String?
)

data class CollectArticleInfo(
    val count: Int
)

data class UserInfo(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)