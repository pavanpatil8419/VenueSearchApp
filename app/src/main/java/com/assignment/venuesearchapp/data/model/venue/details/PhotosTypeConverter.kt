package com.assignment.venuesearchapp.data.model.venue.details

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class PhotosTypeConverter : Serializable{

    @TypeConverter // note this annotation
    fun fromPhotos(photos: Photos?): String? {
        if (photos == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Photos?>() {}.type
        return gson.toJson(photos, type)
    }

    @TypeConverter // note this annotation
    fun toPhotos(photosValueString: String?): Photos? {
        if (photosValueString == null) {
            return null
        }
        val gson = Gson()
        val type= object : TypeToken<Photos?>() {}.type
        return gson.fromJson<Photos>(photosValueString, type)
    }
}