package com.ajay.mediapedia.utils

class Constants {

    companion object {

        /*
        Web constants
         */
        const val CONTENT_TYPE_JSON: String = "application/json;charset=utf-8"

        // Movie
        const val MOVIE_BASE_URL = "https://api.themoviedb.org/3/"
        const val MOVIE_IMAGE_W500_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        const val MOVIE_IMAGE_ORIGINAL_BASE_URL = "https://image.tmdb.org/t/p/original/"

        // Music
        const val MUSIC_BASE_URL = "https://api.spotify.com/v1/"

        //Spotify
        const val SPOTIFY_LOGIN_URL = "https://accounts.spotify.com/api/token/"
        const val SPOTIFY_GLOBAL_TOP50_ID = "37i9dQZEVXbMDoHDwVN2tF"
    }
}