package com.novatec.pitakpass.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.novatec.pitakpass.data.source.PostFilterPagingSource
import com.novatec.pitakpass.domain.model.Filter
import com.novatec.pitakpass.remote.AuthorizedApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostFilterRepository @Inject constructor(private val authorizedApiService: AuthorizedApiService) {

    fun getFilteredPosts(filter: LiveData<Filter>) =
        Pager(config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = { PostFilterPagingSource(authorizedApiService, filter) }).liveData
}