package com.ajay.mediapedia.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Query("page") page: Int
    ): Call<ResponseBody>

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String,
        @Query("page") page: Int
    ): Call<ResponseBody>
}