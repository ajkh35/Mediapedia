package com.ajay.mediapedia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ajay.mediapedia.data.model.Movie
import com.ajay.mediapedia.data.model.MovieNetworkDto
import com.ajay.mediapedia.data.repositories.MovieRepository
import org.modelmapper.ModelMapper

class SharedViewModel(application: Application): AndroidViewModel(application) {

    /**
     * Method to get popular movies
     */
    fun getPopularMovies(page: Int): MutableLiveData<List<MovieNetworkDto>> {
        return MovieRepository.getInstance(getApplication()).getPopularMoviesFromApi(page)
    }

    /**
     * Method to get now playing movies
     */
    fun getNowPlayingMovies(page: Int): MutableLiveData<List<MovieNetworkDto>> {
        return MovieRepository.getInstance(getApplication()).getNowPlayingFromApi(page)
    }

    /**
     * Method to load more now playing movies
     */
    fun getMoreNowPlayingMovies(page: Int) {
        MovieRepository.getInstance(getApplication()).getMoreNowPlayingFromApi(page)
    }

    /**
     * Method to get more popular movies
     */
    fun getMorePopularMovies(page: Int) {
        MovieRepository.getInstance(getApplication()).getMorePopularMoviesFromApi(page)
    }
}