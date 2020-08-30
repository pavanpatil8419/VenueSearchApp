package com.assignment.venuesearchapp.presentation.venue.search

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.venuesearchapp.domain.usecase.SearchVenueUseCase
import kotlinx.coroutines.CoroutineDispatcher

class VenueViewModelFactory(
    private val searchVenueUseCase: SearchVenueUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher,
    private val isNetworkAvailable:Boolean
) : ViewModelProvider.Factory {

    @VisibleForTesting
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VenueViewModel(searchVenueUseCase, ioDispatcher, mainDispatcher, isNetworkAvailable) as T
    }
}