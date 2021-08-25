package com.example.f1.ui.navigation

import android.content.res.Configuration.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1.ui.components.TelemetrySessions
import com.example.f1.ui.theme.F1Theme
import com.google.accompanist.insets.statusBarsHeight

@Composable
fun Telemetry(
    onClickNewSession: () -> Unit,
    onClickTelemetrySession: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Spacer(Modifier.statusBarsHeight(additional = 24.dp))

            Text(text = "Telemetry", style = MaterialTheme.typography.h2)
            NewTelemetrySession(
                onClick = onClickNewSession,
                modifier = Modifier.height(96.dp)
            )

            TelemetrySessions(onClickTelemetrySession = onClickTelemetrySession)
        }
    }
}

@Composable
fun NewTelemetrySession(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(
        topStart = 6.dp, topEnd = 36.dp, bottomStart = 36.dp, bottomEnd = 24.dp
    )
) {
    Surface(
        elevation = 0.dp,
        shape = shape,
        modifier = modifier,
        color = MaterialTheme.colors.secondary
    ) {
        Row(modifier = modifier.clickable(onClick = onClick)) {
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
                    text = "Text",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
//                        text = stringResource(
//                            R.string.course_step_steps,
//                            course.step,
//                            course.steps
//                        ),
                        text = "Text",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                }
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TelemetryPreview() {
    F1Theme {
        Telemetry(onClickNewSession = {}, onClickTelemetrySession = {})
    }
}
