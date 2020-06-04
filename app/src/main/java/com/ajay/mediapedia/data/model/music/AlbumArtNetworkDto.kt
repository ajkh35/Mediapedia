package com.ajay.mediapedia.data.model.music

import com.google.gson.annotations.SerializedName

data class AlbumArtNetworkDto(
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("url") val url: String
) {
    fun toAlbumArt(): AlbumArt = AlbumArt(
        height = this.height,
        width = this.width,
        url = this.url
    )
}