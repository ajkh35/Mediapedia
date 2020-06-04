package com.ajay.mediapedia.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ajay.mediapedia.R
import com.ajay.mediapedia.data.model.music.TrackNetworkDto
import com.ajay.mediapedia.network.ApiClient
import com.ajay.mediapedia.utils.Constants
import com.ajay.mediapedia.utils.PreferenceManager
import com.ajay.mediapedia.utils.SingletonHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicRepository private constructor(context: Context){

    private val TAG: String = javaClass.simpleName
    private val mPreferenceManager = PreferenceManager(context)

    private val GlobalTop50TracksList: MutableLiveData<List<TrackNetworkDto>> = MutableLiveData(ArrayList())

    fun getGlobalTop50FromApi(): MutableLiveData<List<TrackNetworkDto>> {
        val call = ApiClient.getMusicApi().getTracks(
            "Bearer ${mPreferenceManager.getStringPref(PreferenceManager.KEY_SPOTIFY_AUTH_TOKEN)}",
            Constants.SPOTIFY_GLOBAL_TOP50_ID)

        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonObjectRes = JSONObject(response.body()!!.string())
                    val itemsArray = jsonObjectRes.getJSONObject("tracks").getJSONArray("items")
                    val tracksList = ArrayList<TrackNetworkDto>()
                    for(i in 0 until itemsArray.length()) {
                        val trackJson = (itemsArray[i] as JSONObject).getString("track")
                        val trackNetworkDto = Gson().fromJson<TrackNetworkDto>(trackJson, TrackNetworkDto::class.java)
                        tracksList.add(trackNetworkDto)
                    }

                    GlobalTop50TracksList.postValue(tracksList)
                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }

        })

        return GlobalTop50TracksList
    }

    companion object: SingletonHolder<MusicRepository, Context>(::MusicRepository)
}