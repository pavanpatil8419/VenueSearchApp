package com.assignment.venuesearchapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class VenueSearchApplication  : Application() {

    override fun onCreate() {
        super.onCreate()

        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(this)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        shutDownFresco()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        shutDownFresco()
    }

    private fun shutDownFresco() {
        if (Fresco.hasBeenInitialized()) {
            Fresco.shutDown()
        }
    }
}