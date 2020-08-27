package com.assignment.venuesearchapp.data.model.venue.details


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("venue")
    val venueDetails: VenueDetails
)