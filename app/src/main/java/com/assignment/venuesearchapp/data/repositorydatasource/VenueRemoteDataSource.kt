package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.SearchResult
import retrofit2.Response

interface VenueRemoteDataSource {

    suspend fun searchVenues(
        near: String,
        radius: String,
        limitResults: Int): Response<SearchResult>

    suspend fun getVenueDetails(venueId: String): Response<VenueDetailsResponse>


}