package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venues.Venue

interface VenueLocalDataSource {

    suspend fun getSavedVenuesFromDB(): List<Venue>

    suspend fun saveVenueToDB(venueList:List<Venue>)

    suspend fun clearAllFromDB()

    suspend fun updateVenueDetailsById(venueID:String, venueDetails:VenueDetails)

    suspend fun getVenueDetailsById(venueID:String):Venue

}