package com.example.service.Net

import com.example.service.model.UserInfoResponse
import com.example.service.repo.UserInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
interface NetWorkService {

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun loginWanAndroid(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<UserInfo>


    @POST("/user/register")
   @FormUrlEncoded
   fun registerWanAndroid(
       @Field("username") username: String,
       @Field("password") password: String,
       @Field("repassword") repassowrd: String
   ): Call<UserInfo>

    @GET("/user/lg/userinfo/json")
    fun getUserInfo(): Call<UserInfoResponse>

}