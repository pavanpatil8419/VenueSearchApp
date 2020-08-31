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
    ioDispatcher: CoroutineDispatcher,
    mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var venueList = MutableLiveData<List<Venue>>()
    val venueListData: LiveData<List<Venue>>
        get() = venueList

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(ioDispatcher + job)
    private val uiScope = CoroutineScope(mainDispatcher + job)

    init {
        venueList.value = listOf()
    }

    fun searchVenue(searchText: String, isNetworkAvailable:Boolean) {
        uiScope.launch {
            try {
                val data = ioScope.async {
                    return@async searchVenueUseCase.searchNearByVenues(
                        searchText,
                        AppConstants.SEARCH_RADIUS,
                        AppConstants.LIMIT_RESULT,
                        isNetworkAvailable
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