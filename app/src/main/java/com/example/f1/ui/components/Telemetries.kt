package com.example.f1.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1.model.TelemetrySession
import com.example.f1.model.TelemetrySessionRepo
import com.example.f1.ui.theme.F1Theme

@Composable
fun TelemetrySessions(modifier: Modifier = Modifier, onClickTelemetrySession: (Long) -> Unit) {
    val telemetrySessions = TelemetrySessionRepo.getTelemetrySessions()
    TelemetrySessions(
        modifier = modifier,
        telemetrySessions = telemetrySessions,
        onClickTelemetrySession = onClickTelemetrySession
    )
}

@Composable
private fun TelemetrySessions(
    modifier: Modifier,
    telemetrySessions: List<TelemetrySession>,
    onClickTelemetrySession: (Long) -> Unit
) {
    Column() {
        Text(text = "Sessions", style = MaterialTheme.typography.h3)

        LazyColumn(modifier = modifier) {
            items(telemetrySessions) { session ->
                TelemetrySessionItem(
                    modifier = Modifier.height(72.dp),
                    session = session,
                    onClickTelemetrySession = onClickTelemetrySession
                )
            }
        }
    }
}

@Composable
private fun TelemetrySessionItem(
    session: TelemetrySession,
    onClickTelemetrySession: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        elevation = 0.dp,
        shape = RoundedCornerShape(
            topStart = 6.dp, topEnd = 36.dp, bottomStart = 36.dp, bottomEnd = 18.dp
        ),
        modifier = modifier,
        color = MaterialTheme.colors.secondary
    ) {
        Row(modifier = modifier.clickable(onClick = { onClickTelemetrySession(session.id) })) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = MaterialTheme.colors.secondary,
                contentDescription = null
            )
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            ) {
                Text(
                    text = session.name,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${session.laps.size} laps",
                        style = MaterialTheme.typography.caption,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 4.dp)
                    )
//                    Icon(
//                        imageVector = Icons.Rounded.AddCircle,
//                        tint = MaterialTheme.colors.primary,
//                        contentDescription = null,
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Text(
//                        text = "Text",
//                        color = MaterialTheme.colors.primary,
//                        style = MaterialTheme.typography.caption,
//                        modifier = Modifier
//                            .padding(start = 8.dp)
//                            .weight(1f)
////                            .wrapContentWidth(Alignment.Start)
//                    )
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
        TelemetrySessions(onClickTelemetrySession = {})
    }
}
