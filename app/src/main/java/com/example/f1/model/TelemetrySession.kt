package com.example.f1.model

import androidx.compose.runtime.Immutable

enum class Sector {
    ONE,
    TWO,
    THREE
}

@Immutable
data class TelemetrySession(
    val id: Long,
    val name: String,
    val laps: List<Lap>
) {
    private val purpleSector1Lap: Long? by lazy { lapWithFastestSector(Sector.ONE)?.id }
    private val purpleSector2Lap: Long? by lazy { lapWithFastestSector(Sector.TWO)?.id }
    private val purpleSector3Lap: Long? by lazy { lapWithFastestSector(Sector.THREE)?.id }

    fun isPurpleSector(lap: Lap, sector: Sector): Boolean {
        return when (sector) {
            Sector.ONE -> purpleSector1Lap == lap.id
            Sector.TWO -> purpleSector2Lap == lap.id
            Sector.THREE -> purpleSector3Lap == lap.id
        }
    }

    private fun lapWithFastestSector(sector: Sector): Lap? {
        return when (sector) {
            Sector.ONE -> laps.minByOrNull { it.sector1Time }
            Sector.TWO -> laps.minByOrNull { it.sector2Time }
            Sector.THREE -> laps.minByOrNull { it.sector3Time }
        }
    }
}

@Immutable
data class Lap(
    val id: Long,
    val lapTime: Long,
    val sector1Time: Long,
    val sector2Time: Long,
    val sector3Time: Long
)

// TODO: Store data in DB
object TelemetrySessionRepo {
    fun getTelemetrySessions(): List<TelemetrySession> = telemetrySessions
    fun getTelemetrySession(id: Long): TelemetrySession? =
        telemetrySessions.firstOrNull { it.id == id }
}

var telemetrySessions = listOf(
    TelemetrySession(
        id = 1L,
        name = "Bahrain",
        laps = listOf(
            Lap(
                id = 1L,
                lapTime = 90L,
                sector1Time = 30L,
                sector2Time = 30L,
                sector3Time = 30L
            ),
            Lap(
                id = 2L,
                lapTime = 100L,
                sector1Time = 25L,
                sector2Time = 35L,
                sector3Time = 40L
            ),
            Lap(
                id = 3L,
                lapTime = 150L,
                sector1Time = 50L,
                sector2Time = 50L,
                sector3Time = 50L
            )
        )
    )
)

