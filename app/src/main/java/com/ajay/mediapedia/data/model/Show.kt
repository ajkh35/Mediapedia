package com.ajay.mediapedia.data.model

data class Show(
    val popularity: Double,
    val voteCount: Long,
    val posterPath: String?,
    val id: Long,
    val backdropPath: String?,
    val originalLang: String,
    val genreIds: Array<Int>,
    val voteAverage: Float,
    val overview: String,
    val originalName: String,
    val name: String,
    val originCountry: Array<String>,
    val firstAirDate: String
)