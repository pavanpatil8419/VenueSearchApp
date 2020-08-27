package com.assignment.venuesearchapp.data.model.venue.details


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: String,
    @SerializedName("createdAt")
    val createdAt: Int,
    @SerializedName("source")
    val source: Source,
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("suffix")
    val suffix: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("user")
    val user: User,
    @SerializedName("visibility")
    val visibility: String
)