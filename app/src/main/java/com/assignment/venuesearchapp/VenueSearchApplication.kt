package com.assignment.venuesearchapp

import android.app.Application
import com.assignment.venuesearchapp.util.ConnectivityHelper
import com.facebook.drawee.backends.pipeline.Fresco

class VenueSearchApplication  : Application() {

    override fun onCreate() {
        super.onCreate()

        ConnectivityHelper.initialize(this)

        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(this)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        ConnectivityHelper.setAppConextToNull()
        shutDownFresco()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        ConnectivityHelper.setAppConextToNull()
        shutDownFresco()
    }

    private fun shutDownFresco() {
        ConnectivityHelper.setAppConextToNull()
        if (Fresco.hasBeenInitialized()) {
            Fresco.shutDown()
        }
    }
}