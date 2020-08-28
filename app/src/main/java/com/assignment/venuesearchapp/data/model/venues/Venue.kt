package com.assignment.venuesearchapp.data.model.venues


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.venuesearchapp.data.model.venue.details.VenueDetails
import com.google.gson.annotations.SerializedName

@Entity(tableName = "near_by_venue_search_results")
data class Venue(
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("venue_details")
    var venue_details: VenueDetails? = null
)