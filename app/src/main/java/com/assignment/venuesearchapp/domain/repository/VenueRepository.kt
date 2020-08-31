package com.assignment.venuesearchapp.domain.repository

import com.assignment.venuesearchapp.data.model.ErrorResponse
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venues.Venue

interface VenueRepository {

    suspend fun searchNearByVenues(near: String, radius: String, limitResults: Int, isNetworkAvailable: Boolean): List<Venue>

    suspend fun getVenueDetails(venueId: String, isNetworkAvailable: Boolean): VenueDetails?

    fun getErrorInfo():ErrorResponse?

}