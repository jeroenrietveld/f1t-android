package com.example.f1.model

import androidx.compose.runtime.Immutable

@Immutable
data class TrackCollection(
    val id: Long,
    val name: String,
    val tracks: List<Track>
)

// TODO: Store data in DB
object TrackCollectionRepo {
    fun getTrackCollections(): List<TrackCollection> = trackCollections
}

private val trackCollection21 = TrackCollection(
    id=1L,
    name="F1 2021",
    tracks=listOf(tracks[0])
)

private val trackCollections = listOf(trackCollection21)