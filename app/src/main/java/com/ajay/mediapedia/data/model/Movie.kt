package com.ajay.mediapedia.data.model

import java.io.Serializable

data class Movie(
    val popularity: Double,
    val voteCount: Long,
    val video: Boolean,
    val posterPath: String?,
    val id: Long,
    val adult: Boolean,
    val backdropPath: String?,
    val originalLang: String,
    val originalTitle: String,
    val genreIds: Array<Int>,
    val title: String,
    val voteAverage: Float,
    val overview: String,
    val releaseDate: String?
): Serializable