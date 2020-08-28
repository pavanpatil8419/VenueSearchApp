package com.assignment.venuesearchapp.data.repositorydatasource

import android.util.Log
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.repository.VenueRepository
import com.assignment.venuesearchapp.util.ConnectivityHelper

class VenueRepositoryImpl(
    private val remoteDataSource: VenueRemoteDataSource,
    private val localDataSource: VenueLocalDataSource
) : VenueRepository {

    override suspend fun searchNearByVenues(
        near: String,
        radius: String,
        limitResults: Int

    ): List<Venue> {
        return if (ConnectivityHelper.isConnectedToNetwork()) {
            fetchNearByVenuesFromRemoteAPI(near, radius, limitResults)
        } else {
            localDataSource.getSavedVenuesFromDB()
        }
    }

    private suspend fun fetchNearByVenuesFromRemoteAPI(
        near: String,
        radius: String,
        limitResults: Int

    ): List<Venue> {

        val venueList = listOf<Venue>()
        try {
            val response = remoteDataSource.searchVenues(near, radius, limitResults)
            if (response.code() == 200) {
                val body = response.body()
                body?.let {
                    localDataSource.clearAllFromDB()
                    localDataSource.saveVenueToDB(it.response.venues)
                    return it.response.venues

                }
            } else {
                Log.i("Error response", "" + response.code())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return venueList
    }

    override suspend fun getVenueDetails(venueId: String): VenueDetails? {
        return if (ConnectivityHelper.isConnectedToNetwork()) {
            fetchVenueDetailsFromRemoteAPI(venueId)
        } else {
            val venue: Venue = localDataSource.getVenueDetailsById(venueId)
            venue.venue_details
        }
    }


    private suspend fun fetchVenueDetailsFromRemoteAPI(venueId: String): VenueDetails? {
        try {
            val response = remoteDataSource.getVenueDetails(venueId)
            if (response.code() == 200) {
                val body = response.body()
                body?.let {
                    localDataSource.updateVenueDetailsById(venueId, it.response.venueDetails)
                    Log.i("Sucess response code", "" + response.code())
                    return it.response.venueDetails
                }
            } else {
                Log.i("Error response", "" + response.code())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }
}