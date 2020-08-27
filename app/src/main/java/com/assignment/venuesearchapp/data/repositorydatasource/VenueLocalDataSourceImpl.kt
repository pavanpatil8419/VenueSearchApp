package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.db.VenueDAO
import com.assignment.venuesearchapp.data.model.venues.Venue

class VenueLocalDataSourceImpl(
    private val venueDAO: VenueDAO
): VenueLocalDataSource {

    override suspend fun getSavedVenuesFromDB(): List<Venue> {
        return venueDAO.getVenues()
    }

    override suspend fun saveVenueToDB(venueList: List<Venue>) {
        return venueDAO.saveVenues(venueList)
    }

    override suspend fun clearAllFromDB() {
        return venueDAO.deleteAllVenues()
    }
}