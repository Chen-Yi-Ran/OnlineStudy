package com.example.net

import com.example.Model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun loginWanAndroid(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<LoginResponse>


    @POST("/user/register")
   @FormUrlEncoded
   fun registerWanAndroid(
       @Field("username") username: String,
       @Field("password") password: String,
       @Field("repassword") repassowrd: String
   ): Call<LoginResponse>



}