package com.ajay.mediapedia.data.model.music

import com.google.gson.annotations.SerializedName

data class TrackNetworkDto(
    @SerializedName("name") val name: String,
    @SerializedName("duration_ms") val duration: String,
    @SerializedName("explicit") val explicit: Boolean,
    @SerializedName("is_local") val isLocal: Boolean,
    @SerializedName("id") val id: String,
    @SerializedName("href") val url: String,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("track_number") val trackNumber: Int,
    @SerializedName("type") val type: String,
    @SerializedName("album") val albumNetworkDto: AlbumNetworkDto,
    @SerializedName("artists") val artistsNetworkDto: List<ArtistNetworkDto>
) {
    fun toTrack(): Track = Track(
        name = this.name,
        duration = this.duration,
        explicit = this.explicit,
        isLocal = this.isLocal,
        id = this.id,
        url = this.url,
        album = this.albumNetworkDto.toAlbum(),
        artists = getArtists(),
        popularity = this.popularity,
        type = this.type,
        trackNumber = this.trackNumber
    )

    private fun getArtists(): List<Artist> {
        val artists = ArrayList<Artist>()
        for(artistDto in this.artistsNetworkDto) {
            artists.add(artistDto.toArtist())
        }
        return artists
    }
}