package com.ajay.mediapedia.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ajay.mediapedia.R
import com.ajay.mediapedia.data.model.NetworkMovieDto
import com.ajay.mediapedia.network.ApiClient
import com.ajay.mediapedia.utils.Constants
import com.ajay.mediapedia.utils.SingletonHolder
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository private constructor(context: Context){

    private val mContext = context
    private val mPopularMoviesList: MutableLiveData<List<NetworkMovieDto>> = MutableLiveData()

    init {
        mPopularMoviesList.postValue(ArrayList())
    }

    fun getPopularMoviesFromApi(): MutableLiveData<List<NetworkMovieDto>> {
        ApiClient.getMovieApi().getPopularMovies("Bearer " + mContext.getString(R.string.tmdb_access_token),
            Constants.CONTENT_TYPE_JSON).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(javaClass.simpleName, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonResponse = response.body()!!.string()
                    val jsonObject = JSONObject(jsonResponse)
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val moviesList = Gson().fromJson<List<NetworkMovieDto>>(jsonArray,
                            object: TypeToken<List<NetworkMovieDto>>() {}.type)

                        Log.d("TAG>>>", moviesList[0].title)
                        mPopularMoviesList.postValue(moviesList)
                    }

                } else {
                    Log.d(javaClass.simpleName, response.errorBody()!!.string())
                }
            }
        })

        return mPopularMoviesList
    }

    companion object: SingletonHolder<MovieRepository, Context>(::MovieRepository)
}
