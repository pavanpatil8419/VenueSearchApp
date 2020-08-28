package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.api.RemoteAPIService
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.SearchResult
import com.assignment.venuesearchapp.data.model.venues.VenueList
import retrofit2.Response

class VenueRemoteDataSourceImpl(
    private val clientID: String,
    private val clientSecret: String,
    private val apiService: RemoteAPIService
) : VenueRemoteDataSource {
    override suspend fun searchVenues(
        near: String,
        radius: String,
        limitResults: Int
    ): Response<SearchResult> {
        return apiService.searchVenue(
            clientID,
            clientSecret,
            near,
            radius,
            limitResults,
            "20201130"
        )
    }

    override suspend fun getVenueDetails(venueId: String): Response<VenueDetailsResponse> {
        return apiService.getVenueDetails(venueId, clientID, clientSecret, "20201130")
    }
}