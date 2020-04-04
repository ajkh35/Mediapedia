package com.ajay.mediapedia.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Header("Authorization") auth: String,
        @Header("Content-Type") contentType: String
    ): Call<ResponseBody>
}