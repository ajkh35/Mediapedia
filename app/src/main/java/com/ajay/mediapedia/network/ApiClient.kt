package com.ajay.mediapedia.network

import com.ajay.mediapedia.BuildConfig
import com.ajay.mediapedia.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiClient {

    private val mMovieApi: MovieApi
    private val mMusicApi: MusicApi

    init {
        val client = OkHttpClient.Builder().apply {
            if(BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(interceptor)
            }
        }.build()

        // Movie Api
        mMovieApi = Retrofit.Builder()
            .baseUrl(Constants.MOVIE_BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)

        // Music Api
        mMusicApi = Retrofit.Builder()
            .baseUrl(Constants.MUSIC_BASE_URL)
            .client(client)
            .build()
            .create(MusicApi::class.java)
    }

    fun getMovieApi(): MovieApi {
        return mMovieApi
    }

    fun getMusicApi(): MusicApi {
        return mMusicApi
    }
}
