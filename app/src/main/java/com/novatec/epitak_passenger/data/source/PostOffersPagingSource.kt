package com.novatec.epitak_passenger.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novatec.epitak_passenger.remote.AuthApiService
import com.novatec.epitak_passenger.remote.model.OfferDTO
import com.novatec.epitak_passenger.core.enums.EOfferStatus
import retrofit2.HttpException
import java.io.IOException

private const val POST_OFFER_STARTING_PAGE_INDEX = 0

class PostOffersPagingSource(
    private val authApiService: AuthApiService,
    private val id: Long
) : PagingSource<Int, OfferDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OfferDTO> {
        val position = params.key ?: POST_OFFER_STARTING_PAGE_INDEX

        return try {
            val response = authApiService.getOffersForPost(id, position, params.loadSize)
            var offers = response.data
            if (!offers.isNullOrEmpty()) {
                offers = offers.filter {
                    it.status != EOfferStatus.REJECTED
                }.sortedBy {
                    it.status
                }
            }
            LoadResult.Page(
                data = offers!!,
                prevKey = if (position == POST_OFFER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (offers.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, OfferDTO>): Int? {
        return state.anchorPosition
    }
}