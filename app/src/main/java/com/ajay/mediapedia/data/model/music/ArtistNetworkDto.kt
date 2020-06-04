package com.ajay.mediapedia.data.model.music

import com.google.gson.annotations.SerializedName

data class ArtistNetworkDto(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String,
    @SerializedName("href") val infoUrl: String,
    @SerializedName("type") val type: String
) {
    fun toArtist(): Artist = Artist(
        name = this.name,
        id = this.id,
        infoUrl = this.infoUrl,
        type = this.type
    )
}