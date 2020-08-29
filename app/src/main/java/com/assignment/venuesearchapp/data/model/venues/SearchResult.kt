package com.assignment.venuesearchapp.data.model.venues


import com.assignment.venuesearchapp.data.model.Meta
import com.google.gson.annotations.SerializedName

data class SearchResult (
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: VenueList
)