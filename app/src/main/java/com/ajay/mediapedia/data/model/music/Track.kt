package com.ajay.mediapedia.data.model.music

data class Track(
    val name: String,
    val duration: String,
    val explicit: Boolean,
    val isLocal: Boolean,
    val id: String,
    val url: String,
    val popularity: Int,
    val trackNumber: Int,
    val type: String,
    val album: Album,
    val artists: List<Artist>
)