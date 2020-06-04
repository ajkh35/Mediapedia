package com.ajay.mediapedia.data.model.music

data class Album(
    val name: String,
    val releaseDate: String,
    val totalTracks: Int,
    val type: String,
    val albumType: String,
    val albumInfoUrl: String,
    val id: String,
    val albumArts: List<AlbumArt>
)