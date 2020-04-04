package com.ajay.mediapedia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ajay.mediapedia.data.model.NetworkMovieDto
import com.ajay.mediapedia.data.repositories.MovieRepository

class MovieViewModel(application: Application): AndroidViewModel(application) {

    fun getPopularMovies(): MutableLiveData<List<NetworkMovieDto>> {
        return MovieRepository.getInstance(getApplication()).getPopularMoviesFromApi()
    }
}