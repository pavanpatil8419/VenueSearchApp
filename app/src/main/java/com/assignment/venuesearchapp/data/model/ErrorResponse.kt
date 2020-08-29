package com.assignment.venuesearchapp.data.model


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("meta")
    val meta: Meta
)