package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.domain.model.Place
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class GetPlacesFeed(val repository: PlaceRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<Place>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<Place>> {
        return repository.getPlacesAutocomplete(params[Constants.TXT_PLACE]!!)
    }
}