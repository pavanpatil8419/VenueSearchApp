package com.assignment.venuesearchapp.presentation.venue.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.usecase.SearchVenueUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VenueViewModel(private val searchVenueUseCase: SearchVenueUseCase): ViewModel() {
    private val limit = 10
    private val radius: String = "1000"
    private var venueList = MutableLiveData<List<Venue>>()

    val venueListData: LiveData<List<Venue>>
    get() = venueList

    init{
        venueList.value = listOf()
    }

    fun searchVenue( searchtext:String){
        CoroutineScope(Dispatchers.IO).launch {
            val venues = searchVenueUseCase.searchNearByVenues(searchtext, radius, limit)
            withContext(Dispatchers.Main) {
                venueList.value = venues
            }
        }
    }
}