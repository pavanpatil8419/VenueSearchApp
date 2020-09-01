package com.assignment.venuesearchapp.data.repositorydatasource

import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetailsResponse
import com.assignment.venuesearchapp.data.model.venues.SearchResult
import com.assignment.venuesearchapp.data.model.venues.Venue
import com.assignment.venuesearchapp.util.AppConstants
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class VenueRepositoryImplTest {

    private lateinit var remoteDataSource: VenueRemoteDataSource
    private lateinit var localDataSource: VenueLocalDataSource
    private lateinit var venueRepositoryImpl: VenueRepositoryImpl

    private val searchText = "SearchText"
    private val venueId = "VenueId"
    private val radius = AppConstants.SEARCH_RADIUS
    private val limit = AppConstants.LIMIT_RESULT

    private lateinit var searchResultResponse:Response<SearchResult>
    private lateinit var venueDetailsResponse: Response<VenueDetailsResponse>

    private val errorResponse:String = "{\"meta\":{\"code\":400,\"errorType\":\"failed_geocode\",\"errorDetail\":\"Couldn't geocode param near: adasjkdhsjahdsjadhjk\",\"requestId\":\"5f4ba4e42df029194a45dee6\"},\"response\":{}}"


    @Before
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        searchResultResponse = mockk()
        venueDetailsResponse = mockk()
        venueRepositoryImpl = VenueRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `venue details network not available`() = runBlockingTest{
        coEvery{localDataSource.getVenueDetailsById(venueId)} returns Venue (
            venueId,
            "Test Venue",
            mockk(),
            mockk()
        )
        venueRepositoryImpl.getVenueDetails(venueId,false)
        coVerify { localDataSource.getVenueDetailsById(venueId)}

    }

    @Test
    @ExperimentalCoroutinesApi
    fun `search venue network not available`() = runBlockingTest{
        coEvery{localDataSource.getSavedVenuesFromDB()} returns listOf(
                Venue("1", "Venue_1", location = mockk()),
        Venue("2", "Venue_2", location = mockk()),
        Venue("3", "Venue_3", location = mockk()))

        val venueList = venueRepositoryImpl.searchNearByVenues(searchText, radius, limit, false)
        coVerify { localDataSource.getSavedVenuesFromDB()}
        assertEquals(venueList.size, 3)

    }
    @Test
    @ExperimentalCoroutinesApi
    fun `venues details remote api call success`() = runBlockingTest {
        venueDetailsSuccessResponse()
        val response = venueRepositoryImpl.getVenueDetails(venueId,true)
        val captureVenueId = CapturingSlot<String>()

        coVerify(exactly = 1) {
            venueRepositoryImpl.fetchVenueDetailsFromRemoteAPI(
                capture(captureVenueId),
                remoteDataSource,
                localDataSource
            )
        }

        coVerify { localDataSource.updateVenueDetailsById(any(),any()) }
        assertEquals(captureVenueId.captured, venueId)
        assertNotNull(response)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `search venues remote api call success`() = runBlockingTest {
        searchVenueSuccessResponse()
        val response = venueRepositoryImpl.searchNearByVenues(searchText, radius, limit, true)
        val captureId = CapturingSlot<String>()
        val captureRadius = CapturingSlot<String>()
        val captureLimit = CapturingSlot<Int>()

        coVerify(exactly = 1) {
            venueRepositoryImpl.fetchNearByVenuesFromRemoteAPI(
                capture(captureId),
                capture(captureRadius),
                capture(captureLimit),
                remoteDataSource,
                localDataSource
            )
        }
        assertEquals(captureId.captured, searchText)
        assertEquals(captureRadius.captured, AppConstants.SEARCH_RADIUS)
        assertEquals(captureLimit.captured, AppConstants.LIMIT_RESULT)

        coVerifySequence {
            localDataSource.clearAllFromDB()
            localDataSource.saveVenueToDB(any())
        }

        assertEquals(response, 3)

    }

    @Test
    @ExperimentalCoroutinesApi
    fun `search venues remote api call failure`() = runBlockingTest {
        searchVenuesFailure()
        val response = venueRepositoryImpl.searchNearByVenues(searchText, radius, limit, true)
        assertEquals(response.size, 0)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `venues details remote api call failure`() = runBlockingTest {
        venueDetailsFailure()
        val response = venueRepositoryImpl.getVenueDetails(searchText, true)
        assertNull(response)
    }

    private fun searchVenueSuccessResponse(){
        coEvery { remoteDataSource.searchVenues(any(),any(),any())} returns searchResultResponse
        coEvery { searchResultResponse.isSuccessful } returns  true
        coEvery { localDataSource.clearAllFromDB() } returns Unit
        coEvery { localDataSource.saveVenueToDB(any()) } returns Unit
        coEvery { venueRepositoryImpl.searchVenueSuccessResponse(searchResultResponse, localDataSource)} returns listOf(
            Venue("1", "Venue_1", location = mockk()),
            Venue("2", "Venue_2", location = mockk()),
            Venue("3", "Venue_3", location = mockk())
        )
    }
    private fun venueDetailsSuccessResponse(){
        coEvery { remoteDataSource.getVenueDetails(any())} returns venueDetailsResponse
        coEvery { venueDetailsResponse.isSuccessful} returns  true
        coEvery { localDataSource.updateVenueDetailsById(any(), any()) } returns Unit
        coEvery { venueRepositoryImpl.venueDetailsSuccessResponse(venueId, venueDetailsResponse, localDataSource)} returns VenueDetails(
            venueId,
            "Test Venue",
            mockk(),
            mockk(),
            5.5,
            mockk(),
            ""
        )
    }
    private fun searchVenuesFailure(){
        coEvery { remoteDataSource.searchVenues(any(),any(),any())} returns searchResultResponse
        coEvery { searchResultResponse.isSuccessful } returns  false
        coEvery { venueDetailsResponse.errorBody()?.string()} returns errorResponse
    }
    private fun venueDetailsFailure(){
        coEvery { remoteDataSource.getVenueDetails(any())} returns venueDetailsResponse
        coEvery { venueDetailsResponse.isSuccessful } returns  false
        coEvery { venueDetailsResponse.errorBody()?.string()} returns errorResponse
    }
}