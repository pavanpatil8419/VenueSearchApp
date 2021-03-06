package com.assignment.venuesearchapp.util

class AppConstants {

    companion object {

        const val BASE_API_URL = "https://api.foursquare.com/v2/"
        const val CLIENT_ID = "IJVXYIE1BAGOZYNKHCNK1SOKDETCPMDY4SX4Q02NFD2RQTEK"
        const val CLIENT_SECRET = "PDQ5KT3KHXJPOIZMVXPJVP02KRXSAW4C51345N3E2F1HPFS3"

        const val VENUE_ID = "VENUE_ID"

        // Limit search results to #10
        const val LIMIT_RESULT = 10

        //1000m radius - search results in specified radius
        const val SEARCH_RADIUS = "1000"

        const val API_VERSION_VALID_DATE = "20201231"

        const val IMAGE_SIZE = "300x500"

        const val ERROR_TYPE_NETWOTK_ERROR = "network_error"

    }
}