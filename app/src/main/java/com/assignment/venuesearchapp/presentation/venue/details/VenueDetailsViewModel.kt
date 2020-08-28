package com.assignment.venuesearchapp.presentation.venue.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.domain.usecase.GetVenueDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VenueDetailsViewModel (private val venueDetailsUseCase: GetVenueDetailsUseCase): ViewModel() {

    private var venueDetails = MutableLiveData<VenueDetails>()

    val venueDetailsLiveData: LiveData<VenueDetails>
        get() = venueDetails


    fun searchVenue( venueID:String){
        CoroutineScope(Dispatchers.IO).launch {
            val venue = venueDetailsUseCase.getVenueDetails(venueID)
            withContext(Dispatchers.Main) {
                venueDetails.value = venue
            }
        }
    }
}