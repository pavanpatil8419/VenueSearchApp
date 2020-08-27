package com.assignment.venuesearchapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }

        private val okHttp = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun getRetrofitInstance(domainURL: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(domainURL)
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}