package com.assignment.venuesearchapp.domain.usecase

import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.repository.VenueRepository

class SearchVenueUseCase(private val repository: VenueRepository) {

    suspend fun searchNearByVenues(
        near: String, radius: String,
        limitResults: Int
    ): List<Venue> = repository.searchNearByVenues(near, radius, limitResults)
}