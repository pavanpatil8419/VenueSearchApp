package com.assignment.venuesearchapp.domain.usecase

import com.assignment.venuesearchapp.data.repositorydatasource.VenueRepositoryImpl
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetVenueDetailsUseCaseTest {

    private val repositoryMock: VenueRepositoryImpl = mockk()
    private lateinit var useCaseMock: GetVenueDetailsUseCase
    private val venueId = "ABCD"
    private val isNetworkAvailable:Boolean = true

    @Before
    fun setUp() {
        useCaseMock = GetVenueDetailsUseCase(repositoryMock)
    }

    @Test
    fun `verify repository get venue details function called success`() = runBlockingTest{
        coEvery {useCaseMock.getVenueDetails(any(), isNetworkAvailable) } returns mockk()
        useCaseMock.getVenueDetails(venueId, isNetworkAvailable)
        coVerify { repositoryMock.getVenueDetails(any(), isNetworkAvailable) }
    }

    @Test
    fun `verify repository get venue details function called with correct params success`() = runBlockingTest{
        coEvery {useCaseMock.getVenueDetails(any(), isNetworkAvailable) } returns mockk()
        useCaseMock.getVenueDetails(venueId, isNetworkAvailable)
        val catureId = CapturingSlot<String>()
        coVerify { repositoryMock.getVenueDetails(capture(catureId), isNetworkAvailable) }
        assertEquals(catureId.captured, venueId)
    }
}