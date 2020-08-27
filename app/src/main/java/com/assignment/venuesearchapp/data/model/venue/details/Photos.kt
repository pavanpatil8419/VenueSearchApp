package com.assignment.venuesearchapp.data.model.venue.details


import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("count")
    val count: Int,
    @SerializedName("groups")
    val groups: List<Group>
)