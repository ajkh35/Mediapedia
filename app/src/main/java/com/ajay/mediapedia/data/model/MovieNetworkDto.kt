package com.ajay.mediapedia.data.model

import com.google.gson.annotations.SerializedName

data class MovieNetworkDto(
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Long,
    @SerializedName("video") val video: Boolean,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("id") val id: Long,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val originalLang: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genreIds: Array<Int>,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String) {

    fun toMovie(): Movie = Movie(
        popularity = this.popularity,
        voteCount = this.voteCount,
        adult = this.adult,
        video = this.video,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        id = this.id,
        originalLang = this.originalLang,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteAverage = this.voteAverage
    )
}
