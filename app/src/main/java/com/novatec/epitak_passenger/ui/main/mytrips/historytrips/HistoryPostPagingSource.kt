package com.novatec.epitak_passenger.ui.main.mytrips.historytrips

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.remote.AuthorizedApiService

private const val POST_OFFER_STARTING_PAGE_INDEX = 0

class HistoryPostPagingSource(private val authorizedApiService: AuthorizedApiService) :
    PagingSource<Int, PassengerPost>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PassengerPost> {
        val position = params.key ?: POST_OFFER_STARTING_PAGE_INDEX

        return try {
            val response =
                authorizedApiService.getHistoryPosts(position, params.loadSize)
            val posts = response.data?.data
            LoadResult.Page(
                data = posts!!,
                prevKey = if (position == POST_OFFER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (posts.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, PassengerPost>): Int? {
        return state.anchorPosition
    }
}