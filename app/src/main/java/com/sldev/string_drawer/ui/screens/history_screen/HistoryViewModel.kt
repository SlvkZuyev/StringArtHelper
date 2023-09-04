package com.sldev.string_drawer.ui.screens.history_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sldev.string_drawer.data.SnapshotsRepository
import com.sldev.string_drawer.models.Snapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HistoryScreenState(
    val snapshots: List<Snapshot> = listOf()
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val snapshotsRepository: SnapshotsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryScreenState())
    val state: StateFlow<HistoryScreenState> = _state.asStateFlow()

    init {
        loadSnapshots()
    }

    private fun loadSnapshots() {
        viewModelScope.launch {
            _state.update {
                it.copy(snapshots = snapshotsRepository.getAllSnapshots())
            }
        }
    }
}