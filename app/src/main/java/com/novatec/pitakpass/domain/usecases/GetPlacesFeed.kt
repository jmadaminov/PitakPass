package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.domain.model.Place
import com.novatec.pitakpass.domain.repository.PlaceRepository
import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class GetPlacesFeed(val repository: PlaceRepository) :
    UseCaseWithParams<String, ResultWrapper<List<Place>>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<List<Place>> {
        return repository.getPlacesAutocomplete(params)
    }
}