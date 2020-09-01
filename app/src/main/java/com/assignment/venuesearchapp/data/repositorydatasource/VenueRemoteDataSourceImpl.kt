package com.assignment.venuesearchapp.data.repositorydatasource

import android.util.Log
import com.assignment.venuesearchapp.data.api.RemoteAPIService
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.SearchResult
import com.assignment.venuesearchapp.util.AppConstants
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
            near,
            radius,
            limitResults,
            AppConstants.CLIENT_ID,
            AppConstants.CLIENT_SECRET,
            AppConstants.API_VERSION_VALID_DATE)
    }

    override suspend fun getVenueDetails(venueId: String): Response<VenueDetailsResponse> {
        return apiService.getVenueDetails(
            venueId,
            clientID,
            clientSecret,
            AppConstants.API_VERSION_VALID_DATE
        )
    }
}