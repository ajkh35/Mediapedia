package com.ajay.mediapedia.data.model

import com.google.gson.annotations.SerializedName

data class ShowNetworkDto(
    @SerializedName("origin_country") val originCountry: Array<String>,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("name") val name: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Long,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("id") val id: Long,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("original_language") val originalLang: String,
    @SerializedName("genre_ids") val genreIds: Array<Int>) {

    fun toShow(): Show = Show(
        popularity = this.popularity,
        voteCount = this.voteCount,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        id = this.id,
        originalLang = this.originalLang,
        overview = this.overview,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        firstAirDate = this.firstAirDate,
        name = this.name,
        originalName = this.originalName,
        originCountry = this.originCountry
    )
}