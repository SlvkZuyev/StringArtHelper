package com.sldev.string_drawer.ui.screens.instruction_screen

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import com.sldev.string_drawer.data.InstructionRepository
import com.sldev.string_drawer.models.InstructionProgress
import com.sldev.string_drawer.textToSpeech.TextSpeaker
import com.sldev.string_drawer.utils.Looper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class InstructionScreenState(
    val currentStepName: String = "",
    val currentStepIndex: Int = -1,
    val totalSteps: Int = -1,
    val progressUntilNextStep: Float = 0f,
    val isPaused: Boolean = false,
    var looperDelay: Long = 6000
)

@HiltViewModel
class InstructionScreenViewModel @Inject constructor(
    private val repository: InstructionRepository,
    private val textSpeaker: TextSpeaker
) : ViewModel() {

    private val steps: MutableList<Int> = mutableListOf()
    private val looper = Looper(onLoop = ::onLooperStep, scope = viewModelScope)

    private val _state = MutableStateFlow(InstructionScreenState())
    val state: StateFlow<InstructionScreenState> = _state.asStateFlow()

    private val looperDelta = 500

    fun initializeSteps(steps: List<Int>, index: Int) {
        this.steps.addAll(steps)
        _state.update { it.copy(currentStepIndex = index, totalSteps = steps.size) }
    }

    fun start() {
        looper.startLooper()
        looper.delay = state.value.looperDelay
    }

    fun increaseDelay() {
        _state.update {
            val newDelay = it.looperDelay + looperDelta
            looper.delay = newDelay
            it.copy(looperDelay = newDelay)
        }
    }

    fun decreaseDelay() {
        _state.update {
            var newDelay = it.looperDelay - looperDelta
            if (newDelay < 500) {
                newDelay = 500
            }
            looper.delay = newDelay
            it.copy(looperDelay = newDelay)
        }
    }

    fun goToNextStep() {
        val nextStepIndex = state.value.currentStepIndex + 1
        if (nextStepIndex > steps.lastIndex) {
            return
        }
        _state.update {
            it.copy(
                currentStepName = stepIndexToStepName(steps[nextStepIndex]),
                currentStepIndex = nextStepIndex,
                progressUntilNextStep = 0f
            )
        }
    }

    fun goToPreviousStep() {
        val previousStepIndex = state.value.currentStepIndex - 1
        if (previousStepIndex < 0) {
            return
        }
        _state.update {
            it.copy(
                currentStepName = stepIndexToStepName(steps[previousStepIndex]),
                currentStepIndex = previousStepIndex,
                progressUntilNextStep = 0f
            )
        }
    }

    fun pausePlay() {
        if (state.value.isPaused) {
            looper.startLooper()
        } else {
            looper.pause()
            saveProgress()
        }

        _state.update { it.copy(isPaused = !it.isPaused) }
    }

    fun onScreenDisposed() {
        Log.d("SlvkLog", "DISPOSED!!!!!")
        saveProgress()
    }

    fun onLifecycle(event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_PAUSE) {
            saveProgress()
        }
    }

    private fun saveProgress() {
        val progress = InstructionProgress(
            steps = steps,
            currentStepIndex = state.value.currentStepIndex
        )
        repository.saveInstructionProgress(progress)
    }

    private fun onLooperStep() {
        goToNextStep()
        speakOutCurrentStep()
    }

    private fun speakOutCurrentStep() {
        textSpeaker.speakOut(state.value.currentStepName)
    }

    private fun stepIndexToStepName(index: Int): String {
        if (index in 0..59) return "A. $index"
        if (index in 60..119) return "B. ${index % 60}"
        if (index in 120..179) return "C. ${index % 60}"
        if (index in 179..240) return "D. ${index % 60}"
        return "Unknown index: $index"
    }

}