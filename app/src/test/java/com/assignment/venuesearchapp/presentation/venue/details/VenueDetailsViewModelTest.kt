package com.assignment.venuesearchapp.presentation.venue.details

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.repositorydatasource.VenueRepositoryImpl
import com.assignment.venuesearchapp.domain.usecase.GetVenueDetailsUseCase
import com.assignment.venuesearchapp.util.ConnectivityHelper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class VenueDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val venueId = "ABCDEFGH"

    private val dispatcher = Dispatchers.Unconfined

    private lateinit var venueDetailsViewModel: VenueDetailsViewModel
    private val repositoryMock: VenueRepositoryImpl = mockk()
    private val useCaseMock: GetVenueDetailsUseCase = GetVenueDetailsUseCase(repositoryMock)
    @MockK
    private lateinit var application: Application

    private val isNetworkAvailable:Boolean = true

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        venueDetailsViewModel = VenueDetailsViewModel(useCaseMock, dispatcher, dispatcher,isNetworkAvailable)
        coEvery { ConnectivityHelper.isConnectedToNetwork(application)} returns true
    }

    @Test
    fun `venue use case search near by venues called only once success`() {
        coEvery { useCaseMock.getVenueDetails(any(),isNetworkAvailable) } returns mockk()
        venueDetailsViewModel.venueDetailsLiveData.observeForever {}
        venueDetailsViewModel.searchVenue(venueId)
        coVerify(exactly = 1) {
            (useCaseMock.getVenueDetails(any(),isNetworkAvailable))
        }
    }

    @Test
    fun `venues details response success`() {
        coEvery { useCaseMock.getVenueDetails(any(),isNetworkAvailable) } returns VenueDetails(
            venueId,
            "Test Venue",
            mockk(),
            mockk(),
            5.5,
            mockk(),
            ""
        )
        venueDetailsViewModel.venueDetailsLiveData.observeForever {}
        venueDetailsViewModel.searchVenue(venueId)
        assertEquals(venueDetailsViewModel.venueDetailsLiveData.value?.id, venueId)
        assertEquals(venueDetailsViewModel.venueDetailsLiveData.value?.name, "Test Venue")
    }

    @Test
    fun `venues details response failure`() {
        coEvery { useCaseMock.getVenueDetails(any(),isNetworkAvailable) } returns null
        venueDetailsViewModel.venueDetailsLiveData.observeForever {}
        venueDetailsViewModel.searchVenue(venueId)
        assertEquals(venueDetailsViewModel.venueDetailsLiveData.value, null)
    }

    @Test
    fun `venues details response with correct parameters success`() {
        coEvery { useCaseMock.getVenueDetails(any(),isNetworkAvailable) } returns mockk()

        venueDetailsViewModel.venueDetailsLiveData.observeForever {}
        venueDetailsViewModel.searchVenue(venueId)
        val venueId = slot<String>()
        coVerify { useCaseMock.getVenueDetails(capture(venueId),isNetworkAvailable) }
        assertEquals(venueId.captured, this.venueId)
    }
}