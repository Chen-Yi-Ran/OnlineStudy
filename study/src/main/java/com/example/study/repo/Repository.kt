package com.example.study.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blankj.utilcode.util.LogUtils
import com.example.service.model.CategoryProject
import com.example.service.model.ListProjectResponse
import com.example.study.net.GitHubService
import com.example.study.paging.RepoPagingSource
import kotlinx.coroutines.flow.Flow


object Repository {
    private const val PAGE_SIZE = 40

    private val gitHubService = GitHubService.create()

    fun getPagingData(id:Int): Flow<PagingData<ListProjectResponse.Data.DataX>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { RepoPagingSource(gitHubService,id) }
        ).flow
    }




}