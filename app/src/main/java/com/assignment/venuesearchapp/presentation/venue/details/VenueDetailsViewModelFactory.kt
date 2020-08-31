package com.assignment.venuesearchapp.presentation.venue.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.venuesearchapp.domain.usecase.GetVenueDetailsUseCase
import kotlinx.coroutines.CoroutineDispatcher

class VenueDetailsViewModelFactory(
    private val venueDetailsUseCase: GetVenueDetailsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VenueDetailsViewModel(venueDetailsUseCase, ioDispatcher, mainDispatcher) as T
    }
}