package com.assignment.venuesearchapp.domain.usecase

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.domain.repository.VenueRepository

class GetVenueDetailsUseCase(private val repository: VenueRepository) {

    suspend fun getVenueDetails(venueId: String)
            : VenueDetails? = repository.getVenueDetails(venueId)
}