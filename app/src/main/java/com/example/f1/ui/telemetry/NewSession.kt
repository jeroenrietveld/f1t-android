package com.example.f1.ui.telemetry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import com.example.f1.ui.components.Up

@Composable
fun NewSession(
    upPress: () -> Unit,
    newSessionViewModel: NewSessionViewModel = viewModel()
) {
    Box(Modifier.fillMaxSize()) {
        Header()
        Body(newSessionViewModel)
        Up(upPress)
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
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
    viewModel: NewSessionViewModel
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
        Column {
            Spacer(Modifier.height(180.dp))
            Surface(Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = "Receiving: ${viewModel.receivingData}",
                        style = MaterialTheme.typography.overline,
                    )
                }
            }
        }
    }
}
