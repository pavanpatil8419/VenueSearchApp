package com.assignment.venuesearchapp.data.api

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteAPIService {

    @GET("venues/search")
    suspend fun searchVenue(
        @Query("near") near: String,
        @Query("radius") radius: String,
        @Query("limit") limitResults: Int,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version:String
    ): Response<SearchResult>

    @GET("venues/{VENUE_ID}")
    suspend fun getVenueDetails(
        @Path("VENUE_ID") venueId:String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version:String
    ): Response<VenueDetailsResponse>
}
