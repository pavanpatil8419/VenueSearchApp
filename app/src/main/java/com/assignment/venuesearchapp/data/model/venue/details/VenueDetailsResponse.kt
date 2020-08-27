package com.assignment.venuesearchapp.data.model.venue.details


import com.google.gson.annotations.SerializedName

data class VenueDetailsResponse(
    @SerializedName("response")
    val response: Response
)