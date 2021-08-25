package com.example.f1.ui.telemetry

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1.R
import com.example.f1.model.*
import com.example.f1.ui.components.Up
import com.example.f1.ui.theme.F1Theme
import com.google.accompanist.insets.statusBarsPadding
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Composable
fun SessionDetail(
    telemetrySessionId: Long,
    upPress: () -> Unit
) {
    val session = TelemetrySessionRepo.getTelemetrySession(telemetrySessionId)

    Box(Modifier.fillMaxSize()) {
        Header()
        if (session != null) {
            Body(session)
        } else {
            Text(text = stringResource(R.string.resource_not_found))
        }
        Up(upPress)
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(MaterialTheme.colors.secondary, MaterialTheme.colors.secondaryVariant)
                )
            )
    )
}

@Composable
private fun Body(
    session: TelemetrySession,
    modifier: Modifier = Modifier
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
        LazyColumn(modifier = modifier) {
            item {
                Spacer(Modifier.height(180.dp))
            }
            items(session.laps) { lap ->
                LapItem(modifier = Modifier.height(72.dp), session = session, lap = lap)
            }
        }
    }
}

// No need for localization
@SuppressLint("SimpleDateFormat")
@Composable
private fun LapItem(modifier: Modifier = Modifier, session: TelemetrySession, lap: Lap) {
    Surface(
        elevation = 0.dp,
        shape = RoundedCornerShape(
            topStart = 6.dp, topEnd = 36.dp, bottomStart = 36.dp, bottomEnd = 18.dp
        ),
        modifier = modifier,
        color = MaterialTheme.colors.secondary
    ) {
        Row(modifier = modifier.clickable(onClick = {})) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = MaterialTheme.colors.secondary,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                    .weight(1f)
            ) {
                val lapTimeAsDate: Date = Date.from(Instant.ofEpochSecond(lap.lapTime))
                val lapTimeFormatted = SimpleDateFormat("m:s.S").format(lapTimeAsDate)
                Text(
                    text = lapTimeFormatted,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.sizeIn(minHeight = 20.dp)) {
                        val sector1Color =
                            if (session.isPurpleSector(
                                    lap,
                                    Sector.ONE
                                )
                            ) Color.Magenta else Color.Green
                        val sector2Color =
                            if (session.isPurpleSector(
                                    lap,
                                    Sector.TWO
                                )
                            ) Color.Magenta else Color.Green
                        val sector3Color =
                            if (session.isPurpleSector(
                                    lap,
                                    Sector.THREE
                                )
                            ) Color.Magenta else Color.Green

                        drawRect(
                            color = sector1Color, size = Size(80F, 15F)
                        )
                        drawRect(color = sector2Color, Offset(100F, 0F), size = Size(80F, 15F))
                        drawRect(color = sector3Color, Offset(200F, 0F), size = Size(80F, 15F))
                    }
                }
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TelemetryPreview() {
    F1Theme {
        SessionDetail(telemetrySessionId = 1L, upPress = { })
    }
}
