package com.assignment.venuesearchapp.data.model.venue.details

import com.google.gson.annotations.SerializedName

data class VenueDetails(

    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("contact")
    val contact: Contact,
    @SerializedName("location")
    val location: Location,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("description")
    val description: String

//    @SerializedName("canonicalUrl")
//    val canonicalUrl: String,
//    @SerializedName("categories")
//    val categories: List<Category>,
//    @SerializedName("verified")
//    val verified: Boolean,
//    @SerializedName("url")
//    val url: String,
//    @SerializedName("likes")
//    val likes: Likes,
//    @SerializedName("ratingColor")
//    val ratingColor: String,
//    @SerializedName("ratingSignals")
//    val ratingSignals: Int,
//    @SerializedName("storeId")
//    val storeId: String,
)