package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.api.RemoteAPIService
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.VenueList
import retrofit2.Response

class VenueRemoteDataSourceImpl (
    val clientID:String,
    val clientSecret:String,
    val apiService:RemoteAPIService
) : VenueRemoteDataSource {
    override suspend fun searchVenues(
        near: String,
        radius: String,
        limitResults: Int
    ): Response<VenueList> {
        return apiService.searchVenue(clientID, clientSecret,near,radius,limitResults)
    }

    override suspend fun getVenueDetails(venueId: String): Response<VenueDetailsResponse> {
        return apiService.getVenueDetails(venueId, clientID, clientSecret)
    }
}