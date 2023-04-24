package com.example.netdemo.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface JavaBean {

    /**
     *     @POST("/user/login")
     *     @FormUrlEncoded
     *     fun loginWanAndroid(
     *         @Field("username") username: String,
     *         @Field("password") password: String
     *     ): Deferred<LoginResponse>
     * @param
     * @return
     */
    //post请求淘口令
    @FormUrlEncoded
    @POST("/user/login")
    Call<Loginbean> getTicket(@Field("username") String username,@Field("password") String password);//Body 用于post提交数据时的body
}
