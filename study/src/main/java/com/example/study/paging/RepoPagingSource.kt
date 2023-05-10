package com.example.study.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blankj.utilcode.util.LogUtils
import com.example.service.model.ListProjectResponse
import com.example.study.net.GitHubService

class RepoPagingSource(private val gitHubService: GitHubService,private val  id:Int) : PagingSource<Int, ListProjectResponse.Data.DataX>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListProjectResponse.Data.DataX> {
        return try {
            val page = params.key ?: 1 // set page 1 as default
            val pageSize = params.loadSize
            val repoResponse = gitHubService.searchRepos(page,id)

            val repoItems = repoResponse.data.datas
            LogUtils.d(repoItems)
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, ListProjectResponse.Data.DataX>): Int? = null

}
