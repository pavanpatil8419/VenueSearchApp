package com.assignment.venuesearchapp.data.model.venues

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class LocationObjConverter : Serializable {
        @TypeConverter // note this annotation
        fun fromLocation(locationValue: Location?): String? {
            if (locationValue == null) {
                return null
            }
            val gson = Gson()
            val type = object : TypeToken<Location?>() {}.type
            return gson.toJson(locationValue, type)
        }

        @TypeConverter // note this annotation
        fun toLocation(locationValueString: String?): Location? {
            if (locationValueString == null) {
                return null
            }
            val gson = Gson()
            val type= object : TypeToken<Location?>() {}.type
            return gson.fromJson<Location>(locationValueString, type)
        }
}