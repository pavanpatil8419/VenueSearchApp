package com.assignment.venuesearchapp.data.model.venues

import com.google.gson.annotations.SerializedName


data class VenueList(
    @SerializedName("venues")
    val venues: List<Venue>
)