package com.example.study.net

import com.example.service.model.CategoryProject
import com.example.service.model.ListProjectResponse

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface GitHubService {
    @GET("project/list/{page}/json")
    suspend fun searchRepos(   @Path("page") page: Int,
                               @Query("cid") cid: Int): ListProjectResponse

    @GET("project/tree/json")
     fun getCategory():Observable<CategoryProject>

    companion object {
        private const val BASE_URL = "https://wanandroid.com/"

        fun create(): GitHubService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(GitHubService::class.java)
        }
    }
}