package com.ajay.mediapedia.data.model.music

import com.google.gson.annotations.SerializedName

data class AlbumNetworkDto(
    @SerializedName("name") val name: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("total_tracks") val totalTracks: Int,
    @SerializedName("type") val type: String,
    @SerializedName("album_type") val albumType: String,
    @SerializedName("href") val albumInfoUrl: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val albumArtsNetworkDto: List<AlbumArtNetworkDto>
) {
    fun toAlbum(): Album = Album(
        name = this.name,
        releaseDate = this.releaseDate,
        totalTracks = this.totalTracks,
        type = this.type,
        albumType = this.albumType,
        id = this.id,
        albumInfoUrl = this.albumInfoUrl,
        albumArts = getAlbumArts()
    )

    private fun getAlbumArts(): List<AlbumArt> {
        val albumArts = ArrayList<AlbumArt>()
        for(dto in this.albumArtsNetworkDto) {
            albumArts.add(dto.toAlbumArt())
        }
        return albumArts
    }
}