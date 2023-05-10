package com.example.service.Net


import com.example.service.model.UserInfoResponse
import com.example.service.repo.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface NetWorkService {

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun loginWanAndroid(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<UserInfo>


    //注册
    @POST("/user/register")
   @FormUrlEncoded
   fun registerWanAndroid(
       @Field("username") username: String,
       @Field("password") password: String,
       @Field("repassword") repassowrd: String
   ): Call<UserInfo>


   //获取个人信息
    @GET("/user/lg/userinfo/json")
    fun getUserInfo(): Call<UserInfoResponse>


    //获取最新项目信息,默认1到40
//    @GET("search/repositories?sort=stars&q=Android")
//   fun getListProject(@Query("page") page: Int, @Query("per_page") perPage: Int)
//   :RepoResponse

}