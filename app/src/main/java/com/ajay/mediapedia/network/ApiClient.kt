package com.ajay.mediapedia.network

import android.os.Build
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*

object ApiClient {

    private val mMovieApi: MovieApi
    const val BASE_URL = "https://api.themoviedb.org/3/"

    init {
        var tlsspecs = listOf(ConnectionSpec.MODERN_TLS)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            tlsspecs = listOf(ConnectionSpec.COMPATIBLE_TLS)
        }

        val client = OkHttpClient.Builder()
            .connectionSpecs(tlsspecs)
            .build()

        mMovieApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .client(client)
            .build()
            .create(MovieApi::class.java)
    }

    fun getMovieApi(): MovieApi {
        return mMovieApi
    }
}
