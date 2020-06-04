package com.ajay.mediapedia.network

import com.ajay.mediapedia.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MusicApi {

    @GET("?method=chart.gettoptracks")
    fun getTopTracks(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json"
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.SPOTIFY_LOGIN_URL)
    fun spotifyLogin(
        @Header("Authorization") auth: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): Call<ResponseBody>

    @GET("playlists/{playlist_id}")
    fun getTracks(
        @Header("Authorization") auth: String,
        @Path("playlist_id") playlistId: String
    ): Call<ResponseBody>

}
