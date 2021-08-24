package com.example.f1.ui.navigation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1.model.Track
import com.example.f1.model.TrackRepo
import com.example.f1.ui.components.Tracks
import com.example.f1.ui.theme.F1Theme
import com.google.accompanist.insets.statusBarsHeight

@Composable
fun Home(onTrackClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    val tracks = TrackRepo.getTracks()
    Home(tracks = tracks, onTrackClick = onTrackClick, modifier = modifier)
}

@Composable
private fun Home(
    tracks: List<Track>,
    onTrackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box {
            TrackList(tracks = tracks, onTrackClick = onTrackClick)
        }
    }
}

@Composable
private fun TrackList(
    tracks: List<Track>,
    onTrackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            Spacer(Modifier.statusBarsHeight(additional = 56.dp))
            Tracks(
                tracks = tracks,
                onTrackClick = onTrackClick,
                index = 1 // TODO: remove
            )
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    F1Theme {
        Home(onTrackClick = { })
    }
}