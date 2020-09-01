package com.assignment.venuesearchapp.data.model.venue.details

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class VenueDetailsConverter : Serializable {

    @TypeConverter
    fun fromVenueDetails(venueDetailsObj: VenueDetails?): String? {
        if (venueDetailsObj == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<VenueDetails?>() {}.type
        return gson.toJson(venueDetailsObj, type)
    }

    @TypeConverter
    fun toVenueDetails(venueDetailsValueString: String?): VenueDetails? {
        if (venueDetailsValueString == null) {
            return null
        }
        val gson = Gson()
        val type= object : TypeToken<VenueDetails?>() {}.type
        return gson.fromJson<VenueDetails>(venueDetailsValueString, type)
    }
}