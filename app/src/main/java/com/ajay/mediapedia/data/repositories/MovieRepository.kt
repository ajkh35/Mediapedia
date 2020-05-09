package com.ajay.mediapedia.data.repositories

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ajay.mediapedia.R
import com.ajay.mediapedia.data.model.Movie
import com.ajay.mediapedia.data.model.MovieNetworkDto
import com.ajay.mediapedia.data.model.ShowNetworkDto
import com.ajay.mediapedia.network.ApiClient
import com.ajay.mediapedia.utils.Constants
import com.ajay.mediapedia.utils.SingletonHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository private constructor(context: Context){

    private val TAG: String = javaClass.simpleName
    private val mContext = context
    private val mPopularMoviesList: MutableLiveData<List<MovieNetworkDto>> = MutableLiveData(ArrayList())
    private val mNowPlayingList: MutableLiveData<List<MovieNetworkDto>> = MutableLiveData(ArrayList())
    private val mUpcomingList: MutableLiveData<List<MovieNetworkDto>> = MutableLiveData(ArrayList())
    private val mPopularShowsList: MutableLiveData<List<ShowNetworkDto>> = MutableLiveData(ArrayList())

    /**
     * Method to get popular movies from api
     */
    fun getPopularFromApi(page: Int): MutableLiveData<List<MovieNetworkDto>> {
        ApiClient.getMovieApi().getPopularMovies("Bearer ${mContext.getString(R.string.tmdb_access_token)}",
            Constants.CONTENT_TYPE_JSON, page).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonResponse = response.body()!!.string()
                    val jsonObject = JSONObject(jsonResponse)
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val moviesList = Gson().fromJson<List<MovieNetworkDto>>(jsonArray,
                            object: TypeToken<List<MovieNetworkDto>>() {}.type)

                        mPopularMoviesList.postValue(moviesList)
                    }

                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }
        })

        return mPopularMoviesList
    }

    /**
     * Method to get more pages of popular movies from api
     */
    fun getMorePopularMoviesFromApi(page: Int) {
        ApiClient.getMovieApi().getPopularMovies("Bearer ${mContext.getString(R.string.tmdb_access_token)}",
            Constants.CONTENT_TYPE_JSON, page).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonObject = JSONObject(response.body()!!.string())
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val movieDtoList = Gson().fromJson<List<MovieNetworkDto>>(jsonArray,
                            object: TypeToken<List<MovieNetworkDto>>() {}.type)

                        mPopularMoviesList.postValue(movieDtoList)
                    }
                } else {
                    Log.d(TAG, response.errorBody()!!.toString())
                }
            }

        })
    }

    /**
     * Method to get now playing movies from api
     */
    fun getNowPlayingFromApi(page: Int): MutableLiveData<List<MovieNetworkDto>> {
        ApiClient.getMovieApi().getNowPlaying("Bearer " + mContext.getString(R.string.tmdb_access_token),
            Constants.CONTENT_TYPE_JSON, page).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonResponse = response.body()!!.string()
                    val jsonObject = JSONObject(jsonResponse)
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val moviesList = Gson().fromJson<List<MovieNetworkDto>>(jsonArray,
                            object: TypeToken<List<MovieNetworkDto>>() {}.type)

                        mNowPlayingList.postValue(moviesList)
                    }

                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }

        })

        return mNowPlayingList
    }

    /**
     * Method to get more pages of now playing movies from api
     */
    fun getMoreNowPlayingFromApi(page: Int) {
        ApiClient.getMovieApi().getNowPlaying("Bearer ${mContext.getString(R.string.tmdb_access_token)}",
            Constants.CONTENT_TYPE_JSON, page).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonObject = JSONObject(response.body()!!.string())
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val movieDtoList = Gson().fromJson<List<MovieNetworkDto>>(jsonArray,
                            object : TypeToken<List<MovieNetworkDto>>() {}.type
                        )

                        mNowPlayingList.postValue(movieDtoList)
                    }
                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }

        })
    }

    /**
     * Method to get upcoming movies from api
     */
    fun getUpcomingFromApi(page: Int): MutableLiveData<List<MovieNetworkDto>> {
        ApiClient.getMovieApi().getUpcoming("Bearer ${mContext.getString(R.string.tmdb_access_token)}",
            Constants.CONTENT_TYPE_JSON, page).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonResponse = response.body()!!.string()
                    val jsonObject = JSONObject(jsonResponse)
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val moviesList = Gson().fromJson<List<MovieNetworkDto>>(jsonArray,
                            object: TypeToken<List<MovieNetworkDto>>() {}.type)

                        mUpcomingList.postValue(moviesList)
                    }
                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }

        })

        return mUpcomingList
    }

    /**
     * Method to get more pages of upcoming movies from api
     */
    fun getMoreUpcomingMoviesFromApi(page: Int) {
        ApiClient.getMovieApi().getUpcoming("Bearer ${mContext.getString(R.string.tmdb_access_token)}",
            Constants.CONTENT_TYPE_JSON, page).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonResponse = response.body()!!.string()
                    val jsonObject = JSONObject(jsonResponse)
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val moviesList = Gson().fromJson<List<MovieNetworkDto>>(jsonArray,
                            object: TypeToken<List<MovieNetworkDto>>() {}.type)

                        mUpcomingList.postValue(moviesList)
                    }
                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }

        })
    }

    /**
     * Method to get popular shows from api
     */
    fun getPopularShows(page: Int): MutableLiveData<List<ShowNetworkDto>> {
        ApiClient.getMovieApi().getPopularShows("Bearer ${mContext.getString(R.string.tmdb_access_token)}",
            Constants.CONTENT_TYPE_JSON, page).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonResponse = response.body()!!.string()
                    val jsonObject = JSONObject(jsonResponse)
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val moviesList = Gson().fromJson<List<ShowNetworkDto>>(jsonArray,
                            object: TypeToken<List<ShowNetworkDto>>() {}.type)

                        mPopularShowsList.postValue(moviesList)
                    }
                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }

        })

        return mPopularShowsList
    }

    /**
     * Method to get popular tv shows from api
     */
    fun getMorePopularShows(page: Int) {
        ApiClient.getMovieApi().getPopularShows("Bearer ${mContext.getString(R.string.tmdb_access_token)}",
            Constants.CONTENT_TYPE_JSON, page).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonResponse = response.body()!!.string()
                    val jsonObject = JSONObject(jsonResponse)
                    if(jsonObject.has("results")) {
                        val jsonArray = jsonObject.getString("results")
                        val moviesList = Gson().fromJson<List<ShowNetworkDto>>(jsonArray,
                            object: TypeToken<List<ShowNetworkDto>>() {}.type)

                        mPopularShowsList.postValue(moviesList)
                    }
                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }

        })
    }

    companion object: SingletonHolder<MovieRepository, Context>(::MovieRepository)
}
