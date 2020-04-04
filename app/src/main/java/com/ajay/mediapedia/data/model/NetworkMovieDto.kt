package com.ajay.mediapedia.data.model

import com.google.gson.annotations.SerializedName

data class NetworkMovieDto(
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NetworkMovieDto

        if (popularity != other.popularity) return false
        if (voteCount != other.voteCount) return false
        if (video != other.video) return false
        if (posterPath != other.posterPath) return false
        if (id != other.id) return false
        if (adult != other.adult) return false
        if (backdropPath != other.backdropPath) return false
        if (originalLang != other.originalLang) return false
        if (originalTitle != other.originalTitle) return false
        if (!genreIds.contentEquals(other.genreIds)) return false
        if (title != other.title) return false
        if (voteAverage != other.voteAverage) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = popularity.hashCode()
        result = 31 * result + voteCount.hashCode()
        result = 31 * result + video.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + adult.hashCode()
        result = 31 * result + backdropPath.hashCode()
        result = 31 * result + originalLang.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + genreIds.contentHashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + releaseDate.hashCode()
        return result
    }
}
