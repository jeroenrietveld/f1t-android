package com.example.f1.ui.telemetry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.f1.R
import com.example.f1.ui.utils.mirroringBackIcon
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun NewSession(
    upPress: () -> Unit,
    newSessionViewModel: NewSessionViewModel = viewModel()
) {
    Box(Modifier.fillMaxSize()) {
        Header()
        Body(newSessionViewModel)
//        Title(snack, scroll.value)
//        Image(snack.imageUrl, scroll.value)
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
                    val viewState by viewModel.viewState.collectAsState()
                    Text(
                        text = "Receiving: ${viewState.receivingData}",
                        style = MaterialTheme.typography.overline,
                    )
                }
            }
        }
    }
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = MaterialTheme.colors.secondaryVariant,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = MaterialTheme.colors.primaryVariant,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}
