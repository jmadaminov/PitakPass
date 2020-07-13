package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.Place
import com.badcompany.pitakpass.domain.repository.PlaceRepository


/** User Login Use Case
 *
 */
class GetPlacesFeed(val repository: PlaceRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<Place>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<Place>> {
        return repository.getPlacesAutocomplete(params[Constants.TXT_TOKEN]!!,
                                                params[Constants.TXT_LANG]!!,
                                                params[Constants.TXT_PLACE]!!)
    }
}