package com.assignment.venuesearchapp.data.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("code")
    val code: Int,
    @SerializedName("errorType")
    val errorType: String,
    @SerializedName("errorDetail")
    val errorDetail: String,
    @SerializedName("requestId")
    val requestId: String
)