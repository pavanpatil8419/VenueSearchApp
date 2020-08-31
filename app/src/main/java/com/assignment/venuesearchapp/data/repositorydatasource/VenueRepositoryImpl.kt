package com.assignment.venuesearchapp.data.repositorydatasource

import android.util.Log
import com.assignment.venuesearchapp.data.model.ErrorResponse
import com.assignment.venuesearchapp.data.model.Meta
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.SearchResult
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.repository.VenueRepository
import com.assignment.venuesearchapp.util.AppConstants
import com.google.gson.GsonBuilder
import retrofit2.Response


class VenueRepositoryImpl(
    private val remoteDataSource: VenueRemoteDataSource,
    private val localDataSource: VenueLocalDataSource
) : VenueRepository {

    private var errorResponse: ErrorResponse? = null

    override suspend fun searchNearByVenues(
        near: String,
        radius: String,
        limitResults: Int,
        isNetworkAvailable: Boolean

    ): List<Venue> {
        //reset the error object for every new request
        errorResponse = null
        return if (isNetworkAvailable) {
            fetchNearByVenuesFromRemoteAPI(
                near,
                radius,
                limitResults,
                remoteDataSource,
                localDataSource
            )
        } else {
            setNetworkErrorResponse()
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
                val rawError  = response.errorBody()
                rawError?.let {
                    errorResponse = errorResponse(rawError.string())
                }
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
            errorResponse = gson.fromJson(errorString, ErrorResponse::class.java)
            errorResponse.let {
                Log.i(
                    "ErrorResponse:: ",
                    " Code - ${it.meta.code} , " +
                            "Error Details - ${it.meta.errorDetail} , " +
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
        //reset error object for every new request
        errorResponse = null
        return if (isNetworkAvailable) {
            fetchVenueDetailsFromRemoteAPI(venueId, remoteDataSource, localDataSource)
        } else {
            setNetworkErrorResponse()
            //try to fetch venue details from DB if available
            val venue: Venue = localDataSource.getVenueDetailsById(venueId)
            venue.venue_details
        }
    }

    override fun getErrorInfo(): ErrorResponse? {
        return errorResponse
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
                val rawError  = response.errorBody()
                rawError?.let {
                    errorResponse = errorResponse(rawError.string())
                }
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
    private fun setNetworkErrorResponse(){
        errorResponse = ErrorResponse(Meta(-1,AppConstants.ERROR_TYPE_NETWOTK_ERROR,"", "" ))
    }
}