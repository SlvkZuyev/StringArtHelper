package com.sldev.string_drawer.ui.screens.instruction_screen

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.sldev.string_drawer.MainActivity
import com.sldev.string_drawer.composables.LifecycleEffect


class InstructionsScreen(val steps: List<Int>, val currentStepIndex: Int) : Screen {
    @Composable
    override fun Content() {
        InstructionsScreenContent(steps, currentStepIndex)
    }
}


@Composable
fun InstructionsScreenContent(
    steps: List<Int>,
    currentStepIndex: Int
) {
    val viewModel: InstructionScreenViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.initializeSteps(steps, currentStepIndex)
        viewModel.start()
    }

    LifecycleEffect(key = true) { event ->
        viewModel.onLifecycle(event)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Index: ${state.currentStepIndex} of ${state.totalSteps}")
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = viewModel::decreaseDelay) {
                Text(text = "-")
            }
            Text(text = "Delay: ${state.looperDelay}")
            Button(onClick = viewModel::increaseDelay) {
                Text(text = "+")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = state.currentStepName, style = MaterialTheme.typography.h1)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = viewModel::goToPreviousStep) {
                Text(text = "Back")
            }
            Button(onClick = viewModel::pausePlay) {
                Text(text = if (state.isPaused) "Play" else "Pause")
            }
            Button(onClick = viewModel::goToNextStep) {
                Text(text = "Next")
            }
        }
    }

    DisposableEffect(key1 = true) {
        onDispose {
            viewModel.onScreenDisposed()
        }
    }
}