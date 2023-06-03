package com.example.service.Net


import com.example.service.model.*
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


    //获取轮播图信息
    @GET("/banner/json")
    fun getBannerInfo():Call<BannerResponse>
    //获取最新项目信息,默认1到40
//    @GET("search/repositories?sort=stars&q=Android")
//   fun getListProject(@Query("page") page: Int, @Query("per_page") perPage: Int)
//   :RepoResponse

    /**
     * 获取自己收藏的文章列表
     * @param page page
     * @return Deferred<HomeListResponse>
     */
    @GET("/lg/collect/list/{page}/json")
    fun getLikeList(
        @Path("page") page: Int
    ): Call<BannerResponse>

    /**
     * 收藏文章
     * @param id id
     * @return Deferred<HomeListResponse>
     */
    @POST("/lg/collect/{id}/json")
    fun addCollectArticle(
        @Path("id") id: Int
    ): Call<BannerResponse>

    /**
     * 收藏站外文章
     * @param title title
     * @param author author
     * @param link link
     * @return Deferred<HomeListResponse>
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    fun addCollectOutsideArticle(
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("link") link: String
    ): Call<BannerResponse>

    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */

    @GET("/article/list/{page}/json")
    fun getHomeList(
        @Path("page") page: Int
    ): Call<HomeResponse>


    /**
     * 教程列表
     * https://www.wanandroid.com/chapter/547/sublist/json
     */
    @GET("chapter/547/sublist/json")
    fun getSublist():Call<SubListResponse>
}