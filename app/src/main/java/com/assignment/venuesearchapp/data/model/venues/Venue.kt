package com.assignment.venuesearchapp.data.model.venues


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "near_by_venue_search_results")
data class Venue(

    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: Location
//    @SerializedName("categories")
//    val categories: List<String>
//    @SerializedName("venuePage")
//    val venuePage: VenuePage
)