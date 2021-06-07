package com.novatec.epitak_passenger.ui.main.mytrips.historytrips

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.novatec.epitak_passenger.remote.AuthApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryPostRepository @Inject constructor(private val authApi: AuthApi) {

    fun getHistoryPosts() =
        Pager(config = PagingConfig(
            pageSize = 25,
            maxSize = 100,
            enablePlaceholders = false
        ),
              pagingSourceFactory = {
                  Log.d("", "")
                  HistoryPostPagingSource(authApi)
              }).liveData
}