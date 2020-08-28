package com.assignment.venuesearchapp.data.model.venue.details

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class GroupListTypeConverter : Serializable {

    @TypeConverter // note this annotation
    fun fromGroupValuesList(groups: List<Group>): String? {
        if (groups == null) {
            return (null)
        }
        val gson = Gson()
        val type = object : TypeToken<List<Group>?>() {}.type
        return gson.toJson(groups, type)
    }

    @TypeConverter // note this annotation
    fun toGroupValueList(groupListValueString: String?): List<Group>? {
        if (groupListValueString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Group>?>() {}.type
        return gson.fromJson<List<Group>>(groupListValueString, type)
    }
}