package com.assignment.venuesearchapp.presentation.venue.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.data.repositorydatasource.VenueRepositoryImpl
import com.assignment.venuesearchapp.domain.usecase.SearchVenueUseCase
import com.assignment.venuesearchapp.util.AppConstants
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class VenueViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchText = "Amsterdam"

    private val dispatcher = Dispatchers.Unconfined

    private lateinit var venueViewModel: VenueViewModel
    private val repositoryMock: VenueRepositoryImpl = mockk()
    private val useCaseMock: SearchVenueUseCase = SearchVenueUseCase(repositoryMock)
    private val isNetworkAvailable = true

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        venueViewModel = VenueViewModel(useCaseMock, dispatcher, dispatcher, isNetworkAvailable)

    }

    @Test
    fun `venue use case search near by venues called only once success`() {
        coEvery { useCaseMock.searchNearByVenues(any(), any(),any(), isNetworkAvailable) } returns listOf()
        venueViewModel.venueListData.observeForever {}
        venueViewModel.searchVenue(searchText)
        coVerify (exactly = 1) {
            (useCaseMock.searchNearByVenues(any(),any(), any(),isNetworkAvailable))
        }
    }

    @Test
    fun `search venues response success`() {
        coEvery { useCaseMock.searchNearByVenues(any(), any(),any(),isNetworkAvailable) } returns listOf(
            Venue("1", "Venue_1", location = mockk()),
            Venue("2", "Venue_2", location = mockk())
        )
        venueViewModel.venueListData.observeForever {}
        venueViewModel.searchVenue(searchText)
        assertEquals(venueViewModel.venueListData.value?.size, 2)
    }

    @Test
    fun `search venues response failure`() {
        coEvery { useCaseMock.searchNearByVenues(any(), any(),any(),isNetworkAvailable) } returns listOf()
        venueViewModel.venueListData.observeForever {}
        venueViewModel.searchVenue(searchText)
        assertEquals(venueViewModel.venueListData.value?.size, 0)
    }

    @Test
    fun `search near by venues called with correct parameters success`() {
        coEvery { useCaseMock.searchNearByVenues(any(), any(),any(),isNetworkAvailable) } returns listOf()

        venueViewModel.venueListData.observeForever {}
        venueViewModel.searchVenue(searchText)

        val searchTextSlot = slot<String>()
        val radiusSlot = slot<String>()
        val limitSlot = slot<Int>()

        coVerify { useCaseMock.searchNearByVenues(capture(searchTextSlot), capture(radiusSlot),capture(limitSlot),isNetworkAvailable) }

        assertEquals(searchTextSlot.captured, searchText)
        assertEquals(radiusSlot.captured, AppConstants.SEARCH_RADIUS)
        assertEquals(limitSlot.captured, AppConstants.LIMIT_RESULT)

    }
}