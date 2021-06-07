package com.novatec.epitak_passenger.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.novatec.epitak_passenger.data.source.PostFilterPagingSource
import com.novatec.epitak_passenger.domain.model.Filter
import com.novatec.epitak_passenger.remote.AuthApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostFilterRepository @Inject constructor(private val authApi: AuthApi) {

    fun getFilteredPosts(filter: LiveData<Filter>) =
        Pager(config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = { PostFilterPagingSource(authApi, filter) }).liveData
}