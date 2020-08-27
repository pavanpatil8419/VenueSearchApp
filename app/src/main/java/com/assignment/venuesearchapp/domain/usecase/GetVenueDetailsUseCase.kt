package com.assignment.venuesearchapp.domain.usecase

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.repository.VenueRepository

class GetVenueDetailsUseCase(val repository: VenueRepository) {

    suspend fun getVenueDetails(venueId: String)
            : VenueDetails? = repository.getVenueDetails(venueId)
}