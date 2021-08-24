package com.example.f1.model

import androidx.compose.runtime.Immutable

@Immutable
data class TelemetrySession (
    val id: Long,
    val name: String
)

// TODO: Store data in DB
object TelemetrySessionRepo {
    fun getTelemetrySessions(): List<TelemetrySession> = telemetrySessions
}

var telemetrySessions = listOf(
    TelemetrySession(id=1L, name="Bahrain")
)

