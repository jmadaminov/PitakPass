package com.novatec.epitak_passenger.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.novatec.epitak_passenger.data.source.PostOffersPagingSource
import com.novatec.epitak_passenger.remote.AuthApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostOffersRepository @Inject constructor(private val authApiService: AuthApiService) {

    fun getOffersForPost(id: Long) =
        Pager(config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = { PostOffersPagingSource(authApiService, id) }).liveData
}