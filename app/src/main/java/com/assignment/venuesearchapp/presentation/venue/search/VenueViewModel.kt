package com.assignment.venuesearchapp.presentation.venue.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.domain.usecase.SearchVenueUseCase
import com.assignment.venuesearchapp.util.AppConstants
import kotlinx.coroutines.*

class VenueViewModel(
    private val searchVenueUseCase: SearchVenueUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var venueList = MutableLiveData<List<Venue>>()
    val venueListData: LiveData<List<Venue>>
        get() = venueList

    private val job = SupervisorJob()
    // IO Scope, it will use a IO thread pool
    private val ioScope = CoroutineScope(ioDispatcher + job)
    private val uiScope = CoroutineScope(mainDispatcher + job)

    init {
        venueList.value = listOf()
    }

    fun searchVenue(searchText: String) {

//        CoroutineScope(Dispatchers.IO).launch {
//            val venues = searchVenueUseCase.searchNearByVenues(searchtext, radius, limit)
//            withContext(Dispatchers.Main) {
//                venueList.value = venues
//            }
//        }
        uiScope.launch {
            venueList.value = listOf()
            try {
                val data = ioScope.async {
                    return@async searchVenueUseCase.searchNearByVenues(
                        searchText,
                        AppConstants.SEARCH_RADIUS,
                        AppConstants.LIMIT_RESULT
                    )
                }.await()
                venueList.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }

}