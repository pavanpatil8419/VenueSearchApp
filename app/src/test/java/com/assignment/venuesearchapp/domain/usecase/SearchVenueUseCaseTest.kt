package com.assignment.venuesearchapp.domain.usecase

import com.assignment.venuesearchapp.data.repositorydatasource.VenueRepositoryImpl
import com.assignment.venuesearchapp.util.AppConstants
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchVenueUseCaseTest {

    private val repositoryMock: VenueRepositoryImpl = mockk()
    private lateinit var useCaseMock: SearchVenueUseCase
    private val searchText = "Pune"
    private val isNetworkAvailable:Boolean = true

    @Before
    fun setUp() {
        useCaseMock = SearchVenueUseCase(repositoryMock)
    }

    @Test
    fun `verify repository get venue details function called success`() = runBlockingTest{
        coEvery {useCaseMock.searchNearByVenues(any(), any(), any(), isNetworkAvailable) } returns listOf()
        useCaseMock.searchNearByVenues(searchText, AppConstants.SEARCH_RADIUS, AppConstants.LIMIT_RESULT, isNetworkAvailable)
        coVerify { repositoryMock.searchNearByVenues(any(), any(), any(), isNetworkAvailable) }
    }

    @Test
    fun `verify repository get search parameters correctly success`() = runBlockingTest{
        coEvery {useCaseMock.searchNearByVenues(any(), any(), any(),isNetworkAvailable) } returns listOf()
        useCaseMock.searchNearByVenues(searchText,AppConstants.SEARCH_RADIUS, AppConstants.LIMIT_RESULT, isNetworkAvailable)
        val captureId = CapturingSlot<String>()
        val captureRadius = CapturingSlot<String>()
        val captureLimit = CapturingSlot<Int>()
        coVerify { repositoryMock.searchNearByVenues(capture(captureId),capture(captureRadius), capture(captureLimit),isNetworkAvailable) }
        assertEquals(captureId.captured, searchText)
        assertEquals(captureRadius.captured, AppConstants.SEARCH_RADIUS)
        assertEquals(captureLimit.captured, AppConstants.LIMIT_RESULT)
    }
}