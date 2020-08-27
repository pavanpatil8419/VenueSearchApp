package com.assignment.venuesearchapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.assignment.venuesearchapp.data.model.venues.LocationObjConverter
import com.assignment.venuesearchapp.data.model.venues.Venue


@Database (entities = [Venue::class], version = 1)
@TypeConverters(LocationObjConverter::class)
abstract class VenueDatabase: RoomDatabase() {

    abstract fun getVenueDAO():VenueDAO

    companion object{
        private var INSTANCE : VenueDatabase ? = null

        fun getInstance(context: Context):VenueDatabase {
            synchronized(this){
                var instance:VenueDatabase? = INSTANCE
                if(instance == null) {
                    instance =
                        Room.databaseBuilder(context, VenueDatabase::class.java, "venue_database")
                            .build()
                }
                return instance
            }
        }
    }
}