package com.ajay.mediapedia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ajay.mediapedia.data.model.MovieNetworkDto
import com.ajay.mediapedia.data.model.ShowNetworkDto
import com.ajay.mediapedia.data.model.music.TrackNetworkDto
import com.ajay.mediapedia.data.repositories.MovieRepository
import com.ajay.mediapedia.data.repositories.MusicRepository

class SharedViewModel(application: Application): AndroidViewModel(application) {

    /**
     * Method to get popular movies
     */
    fun getPopularMovies(page: Int): MutableLiveData<List<MovieNetworkDto>> {
        return MovieRepository.getInstance(getApplication()).getPopularFromApi(page)
    }

    /**
     * Method to get more popular movies
     */
    fun getMorePopularMovies(page: Int) {
        MovieRepository.getInstance(getApplication()).getMorePopularMoviesFromApi(page)
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
     * Method to get upcoming movies
     */
    fun getUpcomingMovies(page: Int): MutableLiveData<List<MovieNetworkDto>> {
        return MovieRepository.getInstance(getApplication()).getUpcomingFromApi(page)
    }

    /**
     * Method to get more upcoming movies
     */
    fun getMoreUpcomingMovies(page: Int) {
        MovieRepository.getInstance(getApplication()).getMoreUpcomingMoviesFromApi(page)
    }

    /**
     * Method to get popular shows
     */
    fun getPopularShows(page: Int): MutableLiveData<List<ShowNetworkDto>> {
        return MovieRepository.getInstance(getApplication()).getPopularShows(page)
    }

    /**
     * Method to get more popular tv shows
     */
    fun getMorePopularShows(page: Int) {
        MovieRepository.getInstance(getApplication()).getMorePopularShows(page)
    }

    /**
     * Method to get Global top 50 tracks
     */
    fun getGlobalTop50Tracks(): MutableLiveData<List<TrackNetworkDto>> {
        return MusicRepository.getInstance(getApplication()).getGlobalTop50FromApi()
    }
}