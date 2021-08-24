package com.example.f1.model

import androidx.compose.runtime.Immutable

@Immutable
data class Track (
    val id: Long,
    val name: String,
    val image: String,
    val contentDescription: String
)

// TODO: Store data in DB
object TrackRepo {
    fun getTracks(): List<Track> = tracks
}

val tracks = listOf(
    Track(
        id=1L,
        name="Bahrain",
        image="",
        contentDescription="Bahrain track"
    )
)