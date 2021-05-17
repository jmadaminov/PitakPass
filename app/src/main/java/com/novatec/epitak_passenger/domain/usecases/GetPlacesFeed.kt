package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.domain.repository.PlaceRepository
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ResultWrapper


/** User Login Use Case
 *
 */
class GetPlacesFeed(val repository: PlaceRepository) :
    UseCaseWithParams<String, ResultWrapper<List<Place>>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<List<Place>> {
        return repository.getPlacesAutocomplete(params)
    }
}