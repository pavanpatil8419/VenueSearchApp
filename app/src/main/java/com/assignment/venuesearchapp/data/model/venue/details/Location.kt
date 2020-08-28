package com.assignment.venuesearchapp.data.model.venue.details


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("formattedAddress")
    val formattedAddress: List<String>

//    @SerializedName("crossStreet")
//    val crossStreet: String,
//    @SerializedName("lat")
//    val lat: Double,
//    @SerializedName("lng")
//    val lng: Double,
//    @SerializedName("postalCode")
//    val postalCode: String,
//    @SerializedName("cc")
//    val cc: String,
//    @SerializedName("city")
//    val city: String,
//    @SerializedName("state")
//    val state: String,
//    @SerializedName("country")
//    val country: String,

)