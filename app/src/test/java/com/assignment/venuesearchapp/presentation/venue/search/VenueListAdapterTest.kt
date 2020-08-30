package com.assignment.venuesearchapp.presentation.venue.search

import android.content.Context
import com.assignment.venuesearchapp.data.model.venues.Venue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class VenueListAdapterTest {

    private lateinit var venueListAdapter: VenueListAdapter

    private val  clickListener = mockk<(Venue) -> Unit>()

    private val venueList = listOf(
        Venue("1", "Venue_1", location = mockk()),
        Venue("2", "Venue_2", location = mockk())
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        venueListAdapter =  spyk(VenueListAdapter(venueList, clickListener))
    }

    @Test
    fun `verify get item count success`(){
        val count =  venueListAdapter.itemCount
        assertEquals(venueList.size, count)
    }

    @Test
    fun `set venue list success` (){
        every{venueListAdapter.notifyDataSetChanged()} returns Unit
        val venueList = listOf(
            Venue("1", "Venue_1", location = mockk()),
            Venue("2", "Venue_2", location = mockk()),
            Venue("3", "Venue_3", location = mockk()),
            Venue("4", "Venue_4", location = mockk())
        )
        venueListAdapter.setVenueList(venueList)
        val count =  venueListAdapter.itemCount
        assertEquals(venueList.size, count)
    }

    @Test
    fun `adapter notify data set changed called success` (){

        every{venueListAdapter.notifyDataSetChanged()} returns Unit
        val venueList = listOf(
            Venue("1", "Venue_1", location = mockk()),
            Venue("2", "Venue_2", location = mockk()),
            Venue("3", "Venue_3", location = mockk()),
            Venue("4", "Venue_4", location = mockk())
        )
        venueListAdapter.setVenueList(venueList)

        verify { venueListAdapter.notifyDataSetChanged()}
    }
}