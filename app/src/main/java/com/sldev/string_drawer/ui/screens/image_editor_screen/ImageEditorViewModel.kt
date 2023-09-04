package com.sldev.string_drawer.ui.screens.image_editor_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sldev.string_drawer.CircularLineEngine
import com.sldev.string_drawer.ImageRadius
import com.sldev.string_drawer.data.InstructionRepository
import com.sldev.string_drawer.data.SnapshotsRepository
import com.sldev.string_drawer.extensions.to2dList
import com.sldev.string_drawer.models.*
import com.sldev.string_drawer.utils.BitmapProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ImageEditorScreenState(
    val linesCalculationResult: LinesCalculationResult = LinesCalculationResult(),
    val engineParameters: EngineParameters = EngineParameters(),
    val imageParameters: ImageParameters = ImageParameters(),
    val isLoading: Boolean = false,
    val loadingProgress: Float = 0f,
    val savedInstructions: InstructionProgress? = null
)


@HiltViewModel
class ImageEditorViewModel @Inject constructor(
    private val snapshotsRepository: SnapshotsRepository,
    private val bitmapProvider: BitmapProvider
) : ViewModel() {
    private val _state = MutableStateFlow(ImageEditorScreenState())
    val state: StateFlow<ImageEditorScreenState> = _state.asStateFlow()
    val imageBuildingJob = Job()


    fun handleImageParametersChange(parameters: ImageParameters) {
        _state.update { it.copy(imageParameters = parameters) }
    }

    fun handleEngineParametersChange(parameters: EngineParameters) {
        _state.update { it.copy(engineParameters = parameters) }
    }

    fun generateNewImage() {
        viewModelScope.launch(imageBuildingJob + Dispatchers.Default) {
            val engine = CircularLineEngine(
                image = bitmapProvider.getBitmap().to2dList(),
                parameters = state.value.engineParameters,
                radius = ImageRadius,
                onProgressChange = ::onProgressChange
            )
            _state.update { it.copy(isLoading = true) }
            val linesCalculationResult = engine.calculateLines()
            saveSnapshotAsync(linesCalculationResult.linesCoordinates)
            _state.update { it.copy(linesCalculationResult = linesCalculationResult) }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun createInstruction() {
        _state.update {
            it.copy(
                savedInstructions = InstructionProgress(
                    steps = it.linesCalculationResult.indexedLines.map { it.first },
                    currentStepIndex = 0
                )
            )
        }
    }

    private fun saveSnapshotAsync(lines: List<Line>) = viewModelScope.launch {
        val currentSnapshot = Snapshot(
            lines = lines,
            engineParameters = state.value.engineParameters,
            imageParameters = state.value.imageParameters,
        )
        snapshotsRepository.addSnapshot(currentSnapshot)
    }

    private fun onProgressChange(progress: Float) {
        _state.update { it.copy(loadingProgress = progress) }
    }
}