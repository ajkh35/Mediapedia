package com.ajay.mediapedia.network

import com.ajay.mediapedia.BuildConfig
import com.ajay.mediapedia.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiClient {

    private val mMovieApi: MovieApi

    init {
        val client = OkHttpClient.Builder()
        if(BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }
        mMovieApi = Retrofit.Builder()
            .baseUrl(Constants.MOVIE_BASE_URL)
            .client(client.build())
            .build()
            .create(MovieApi::class.java)
    }

    fun getMovieApi(): MovieApi {
        return mMovieApi
    }
}
