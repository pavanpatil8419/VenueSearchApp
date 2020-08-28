package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.db.VenueDAO
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venues.Venue

class VenueLocalDataSourceImpl(
    private val venueDAO: VenueDAO
): VenueLocalDataSource {

    override suspend fun getSavedVenuesFromDB(): List<Venue> {
        return venueDAO.getVenues()
    }

    override suspend fun saveVenueToDB(venueList: List<Venue>) {
        venueDAO.saveVenues(venueList)
    }

    override suspend fun clearAllFromDB() {
        venueDAO.deleteAllVenues()
    }

    override suspend fun updateVenueDetailsById(venueID: String, venueDetails: VenueDetails) {
        venueDAO.updateVenueById(venueID,venueDetails)
    }

    override suspend fun getVenueDetailsById(venueID: String):Venue {
        return venueDAO.getVenueById(venueID)
    }

}