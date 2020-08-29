package com.assignment.venuesearchapp.presentation.venue.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.venuesearchapp.domain.usecase.SearchVenueUseCase
import kotlinx.coroutines.CoroutineDispatcher

class VenueViewModelFactory(private val searchVenueUseCase: SearchVenueUseCase, private val ioDispatcher: CoroutineDispatcher, val mainDispatcher: CoroutineDispatcher): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VenueViewModel(searchVenueUseCase, ioDispatcher, mainDispatcher) as T
    }
}