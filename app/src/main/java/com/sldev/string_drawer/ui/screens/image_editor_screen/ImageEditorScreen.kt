package com.sldev.string_drawer.ui.screens.image_editor_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.sldev.string_drawer.composables.DescriptionSlider
import com.sldev.string_drawer.composables.LinesDrawingSurface
import com.sldev.string_drawer.models.EngineParameters
import com.sldev.string_drawer.models.ImageParameters
import com.sldev.string_drawer.ui.screens.history_screen.HistoryScreen
import com.sldev.string_drawer.ui.screens.instruction_screen.InstructionsScreen

class ImageEditorScreen : Screen {
    @Composable
    override fun Content() {
        ImageEditorScreenContent()
    }
}


@Composable
fun ImageEditorScreenContent() {
    val viewModel: ImageEditorViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.current

    LaunchedEffect(key1 = true) {
        viewModel.generateNewImage()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            ImageParametersSection(
                modifier = Modifier.fillMaxWidth(),
                parameters = state.imageParameters,
                onChange = viewModel::handleImageParametersChange
            )


            LinesDrawingSurface(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(350.dp)
                    .padding(16.dp),
                lines = state.linesCalculationResult.linesCoordinates,
                imageParameters = state.imageParameters,
                isLoading = state.isLoading,
                loadingProgress = state.loadingProgress
            )

            EngineParametersSection(
                modifier = Modifier.fillMaxWidth(),
                parameters = state.engineParameters,
                onChange = viewModel::handleEngineParametersChange
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.generateNewImage() }) {
                    Text(text = "Generate", fontSize = 32.sp)
                }
                Button(onClick = { navigator?.push(HistoryScreen()) }) {
                    Text(text = "History", fontSize = 32.sp)
                }
            }
            Button(onClick = { viewModel.createInstruction() }) {
                Text(text = "Generate Instruction", fontSize = 32.sp)
            }
        }
    }
}

@Composable
fun ImageParametersSection(
    modifier: Modifier = Modifier,
    parameters: ImageParameters,
    onChange: (ImageParameters) -> Unit
) {
    Column(modifier = modifier) {
        DescriptionSlider(
            description = "Stroke width: ",
            value = parameters.strokeWidth,
            onChange = { onChange(parameters.copy(strokeWidth = it)) },
            minValue = 0f,
            maxValue = 5f,
            steps = 20,
        )

        DescriptionSlider(
            description = "Alpha: ",
            value = parameters.alpha,
            onChange = { onChange(parameters.copy(alpha = it)) },
            minValue = 0f,
            maxValue = 1f,
            steps = 18,
            stepValue = 0.1f
        )
    }
}

@Composable
fun EngineParametersSection(
    modifier: Modifier = Modifier,
    parameters: EngineParameters,
    onChange: (EngineParameters) -> Unit
) {
    Column(modifier = modifier) {

        DescriptionSlider(
            description = "Gray delta value: ",
            value = parameters.grayDelta,
            onChange = { onChange(parameters.copy(grayDelta = it)) },
            minValue = 0f,
            maxValue = 40f,
            steps = 39
        )

        DescriptionSlider(
            description = "Dark modifier value: ",
            value = parameters.darkModifier,
            onChange = { onChange(parameters.copy(darkModifier = it)) },
            minValue = 0f,
            maxValue = 80f,
            steps = 79
        )

        DescriptionSlider(
            description = "Light modifier: ",
            value = parameters.lightModifier,
            onChange = { onChange(parameters.copy(lightModifier = it)) },
            minValue = -40f,
            maxValue = 10f,
            steps = 49
        )

        DescriptionSlider(
            description = "Number of lines: ",
            value = parameters.linesCount.toFloat(),
            onChange = { onChange(parameters.copy(linesCount = it.toInt())) },
            minValue = 100.toFloat(),
            maxValue = 4000.toFloat(),
            steps = 4999
        )

        DescriptionSlider(
            description = "Minimal ankers distance",
            value = parameters.minimalAnkersDistance.toFloat(),
            onChange = { onChange(parameters.copy(minimalAnkersDistance = it.toInt())) },
            minValue = 1.toFloat(),
            maxValue = 180.toFloat(),
            steps = 180
        )
    }
}
