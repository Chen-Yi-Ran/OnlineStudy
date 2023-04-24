package com.example.netdemo.cookieTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//Retrofit构建器
object ServiceCreator {
    private const val BASE_URL="https://www.wanandroid.com//"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpUtil.getClient())
        .client(OkHttpUtil.getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)

    //泛型实化
    inline fun <reified  T> create():T= create(T::class.java)
}