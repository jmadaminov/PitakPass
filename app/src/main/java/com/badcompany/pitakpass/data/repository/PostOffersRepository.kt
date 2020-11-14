package com.badcompany.pitakpass.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.badcompany.pitakpass.data.source.PostOffersPagingSource
import com.badcompany.pitakpass.remote.AuthorizedApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostOffersRepository @Inject constructor(private val authorizedApiService: AuthorizedApiService) {

    fun getOffersForPost(id: Long) =
        Pager(config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = { PostOffersPagingSource(authorizedApiService, id) }).liveData
}