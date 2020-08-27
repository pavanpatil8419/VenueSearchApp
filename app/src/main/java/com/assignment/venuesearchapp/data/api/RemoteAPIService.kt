package com.assignment.venuesearchapp.data.api

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.VenueList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteAPIService {

   // https://api.foursquare.com/v2/venues/search

    @GET("/search")
    suspend fun searchVenue(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("near") near: String,
        @Query("radius") radius: String,
        @Query("limit") limitResults: Int
    ): Response<VenueList>

    //https://api.foursquare.com/v2/venues/VENUE_ID

    @GET("/search/{VENUE_ID}")
    suspend fun getVenueDetails(
        @Path("VENUE_ID") venueId:String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ): Response<VenueDetailsResponse>



}
