package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.data.model.venues.VenueList
import retrofit2.Response

interface VenueLocalDataSource {

    suspend fun getSavedVenuesFromDB(): List<Venue>

    suspend fun saveVenueToDB(venueList:List<Venue>)

    suspend fun clearAllFromDB()
}