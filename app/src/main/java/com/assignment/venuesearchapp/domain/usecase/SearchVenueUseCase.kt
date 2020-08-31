package com.assignment.venuesearchapp.domain.usecase

import com.assignment.venuesearchapp.data.model.ErrorResponse
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.repository.VenueRepository

class SearchVenueUseCase(private val repository: VenueRepository) {

    suspend fun searchNearByVenues(
        near: String, radius: String,
        limitResults: Int,
        isNetworkAvailable:Boolean
    ): List<Venue>{
      return repository.searchNearByVenues(near, radius, limitResults, isNetworkAvailable)
    }

    fun getErrorInfo(): ErrorResponse?{
        return repository.getErrorInfo()
    }

}