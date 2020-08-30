package com.assignment.venuesearchapp.data.repositorydatasource

import android.util.Log
import com.assignment.venuesearchapp.data.model.ErrorResponse
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.SearchResult
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.repository.VenueRepository
import com.google.gson.GsonBuilder
import retrofit2.Response


class VenueRepositoryImpl(
    private val remoteDataSource: VenueRemoteDataSource,
    private val localDataSource: VenueLocalDataSource
) : VenueRepository {

    private lateinit var errorResponse: ErrorResponse

    override suspend fun searchNearByVenues(
        near: String,
        radius: String,
        limitResults: Int,
        isNetworkAvailable: Boolean

    ): List<Venue> {
        return if (isNetworkAvailable) {
            fetchNearByVenuesFromRemoteAPI(
                near,
                radius,
                limitResults,
                remoteDataSource,
                localDataSource
            )
        } else {
            localDataSource.getSavedVenuesFromDB()
        }
    }

    suspend fun fetchNearByVenuesFromRemoteAPI(
        near: String,
        radius: String,
        limitResults: Int,
        remoteDataSource: VenueRemoteDataSource,
        localDataSource: VenueLocalDataSource
    ): List<Venue> {
        var venueList = listOf<Venue>()
        try {
            val response = remoteDataSource.searchVenues(near, radius, limitResults)
            if (response.isSuccessful) {
                venueList = searchVenueSuccessResponse(response, localDataSource)
            } else {
                errorResponse = errorResponse(response.errorBody().toString())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return venueList
    }

    suspend fun searchVenueSuccessResponse(
        response: Response<SearchResult>,
        localDataSource: VenueLocalDataSource
    ): List<Venue> {
        val body = response.body()
        body?.let {
            localDataSource.clearAllFromDB()
            localDataSource.saveVenueToDB(body.response.venues)
            return it.response.venues
        }
        return listOf()
    }

    private fun errorResponse(errorString: String): ErrorResponse {
        lateinit var errorResponse: ErrorResponse
        errorString.let {
            val gson = GsonBuilder().create()
            errorResponse =
                gson.fromJson(errorString, ErrorResponse::class.java)
            errorResponse.let {
                Log.i(
                    "Error Response:: ",
                    " Code - ${it.meta.code} " +
                            "Error Details - ${it.meta.errorDetail} " +
                            "ErrorType -${it.meta.errorType} "
                )
            }
        }
        return errorResponse
    }

    override suspend fun getVenueDetails(
        venueId: String,
        isNetworkAvailable: Boolean
    ): VenueDetails? {
        return if (isNetworkAvailable) {
            fetchVenueDetailsFromRemoteAPI(venueId, remoteDataSource, localDataSource)
        } else {
            val venue: Venue = localDataSource.getVenueDetailsById(venueId)
            venue.venue_details
        }
    }

    suspend fun fetchVenueDetailsFromRemoteAPI(
        venueId: String,
        remoteDataSource: VenueRemoteDataSource,
        localDataSource: VenueLocalDataSource
    ): VenueDetails? {
        try {
            val response = remoteDataSource.getVenueDetails(venueId)
            return if (response.isSuccessful) {
                venueDetailsSuccessResponse(venueId, response, localDataSource)
            } else {
                errorResponse = errorResponse(response.errorBody().toString())
                null
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }

    suspend fun venueDetailsSuccessResponse(
        venueId: String,
        response: Response<VenueDetailsResponse>,
        localDataSource: VenueLocalDataSource
    ): VenueDetails? {
        val body = response.body()
        body?.let {
            localDataSource.updateVenueDetailsById(venueId, it.response.venueDetails)
            return it.response.venueDetails
        }
        return null
    }
}