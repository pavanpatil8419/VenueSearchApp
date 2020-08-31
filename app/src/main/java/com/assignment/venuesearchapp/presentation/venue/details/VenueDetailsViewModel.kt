package com.assignment.venuesearchapp.presentation.venue.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.venuesearchapp.data.model.ErrorResponse
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.domain.usecase.GetVenueDetailsUseCase
import kotlinx.coroutines.*

class VenueDetailsViewModel(
    private val venueDetailsUseCase: GetVenueDetailsUseCase,
    ioDispatcher: CoroutineDispatcher,
    mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var venueDetails = MutableLiveData<VenueDetails>()

    val venueDetailsLiveData: LiveData<VenueDetails>
        get() = venueDetails

    private var error = MutableLiveData<ErrorResponse>()
    val errorInfo: LiveData<ErrorResponse>
        get() = error

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(ioDispatcher + job)
    private val uiScope = CoroutineScope(mainDispatcher + job)


    fun searchVenue(venueID: String, isNetworkAvailable:Boolean ) {

        uiScope.launch {
            try {
                val data = ioScope.async {
                    return@async venueDetailsUseCase.getVenueDetails(
                        venueID,
                        isNetworkAvailable
                    )
                }.await()
                venueDetails.value = data
                error.value = venueDetailsUseCase.getErrorInfo()
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