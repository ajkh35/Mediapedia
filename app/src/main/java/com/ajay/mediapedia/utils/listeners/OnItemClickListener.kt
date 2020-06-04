package com.ajay.mediapedia.utils.listeners

import com.ajay.mediapedia.data.model.Movie
import com.ajay.mediapedia.data.model.Show

interface OnItemClickListener {

    /**
     * Handle movie click
     */
    fun onItemClick(movie: Movie)

    /**
     * Handle movie click
     */
    fun onItemClick(show: Show)
}