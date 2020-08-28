package com.assignment.venuesearchapp.data.model.venue.details

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class ItemListTypeConverter: Serializable {

    @TypeConverter // note this annotation
    fun fromItemValuesList(items: List<Item> ):String? {
        if (items == null) {
            return (null);
        }
        val gson: Gson = Gson();
        val type = object : TypeToken<List<Item>?>() {}.type
        return gson.toJson(items, type)
    }

    @TypeConverter // note this annotation
    fun toItemValueList(itemListValueString: String?): List<Item>? {
        if (itemListValueString == null) {
            return null
        }
        val gson = Gson()
        val type= object : TypeToken<List<Item>?>() {}.type
        return gson.fromJson<List<Item>>(itemListValueString, type)
    }
}