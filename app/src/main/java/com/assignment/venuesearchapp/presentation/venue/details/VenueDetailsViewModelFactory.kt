package com.assignment.venuesearchapp.presentation.venue.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.venuesearchapp.domain.usecase.GetVenueDetailsUseCase

class VenueDetailsViewModelFactory(
    private val venueDetailsUseCase: GetVenueDetailsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VenueDetailsViewModel(venueDetailsUseCase) as T
    }
}