package com.assignment.venuesearchapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.assignment.venuesearchapp.data.model.venues.Venue

@Dao
interface VenueDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVenues(venues: List<Venue>)

    @Query("DELETE from near_by_venue_search_results")
    suspend fun deleteAllVenues()


    @Query("SELECT * from near_by_venue_search_results where id=:venueId")
    suspend fun getVenueById(venueId:String):Venue

    @Query("UPDATE near_by_venue_search_results SET venue_details=:venueDetails where id=:venueID")
    suspend fun updateVenueById(venueID:String, venueDetails: VenueDetails)

    @Query("SELECT * from near_by_venue_search_results")
    suspend fun getVenues(): List<Venue>

}