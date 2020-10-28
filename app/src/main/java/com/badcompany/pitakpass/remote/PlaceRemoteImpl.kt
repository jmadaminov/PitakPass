package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.data.repository.PlaceRemote
import com.badcompany.pitakpass.domain.model.Place
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PlaceRemoteImpl @Inject constructor(private val apiService: ApiService,
                                          private val authorizedApiService: AuthorizedApiService) :
    PlaceRemote {

    override suspend fun getPlacesAutocomplete(

                                               queryString: String): ResultWrapper<List<Place>> {
        return try {
            val response = authorizedApiService.getPlacesFeed(queryString)
            if (response.code == 1) {
                val places = arrayListOf<Place>()
                response.data!!.forEach {
                    places.add(it)
                }
                ResultWrapper.Success(places)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}