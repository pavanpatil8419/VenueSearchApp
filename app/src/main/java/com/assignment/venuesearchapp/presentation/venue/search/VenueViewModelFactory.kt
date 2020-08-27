package com.assignment.venuesearchapp.presentation.venue.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.venuesearchapp.domain.usecase.GetVenueDetailsUseCase
import com.assignment.venuesearchapp.domain.usecase.SearchVenueUseCase
import com.assignment.venuesearchapp.presentation.venue.details.VenueDetailsViewModel

class VenueViewModelFactory(private val searchVenueUseCase: SearchVenueUseCase): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VenueViewModel(searchVenueUseCase) as T
    }
}