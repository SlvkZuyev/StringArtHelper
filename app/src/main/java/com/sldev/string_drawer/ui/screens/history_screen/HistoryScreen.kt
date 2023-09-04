package com.sldev.string_drawer.ui.screens.history_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.sldev.string_drawer.composables.LinesDrawingSurface
import com.sldev.string_drawer.models.Snapshot

class HistoryScreen: Screen {
    @Composable
    override fun Content(){
        HistoryScreenContent()
    }
}

@Composable
fun HistoryScreenContent() {
    val viewModel: HistoryViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize(), reverseLayout = true) {
        items(state.snapshots) {
            SnapshotItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                snapshot = it
            )
        }
    }
}

@Composable
fun SnapshotItem(modifier: Modifier = Modifier, snapshot: Snapshot) {
    Column(modifier) {
        LinesDrawingSurface(
            modifier = Modifier.height(350.dp),
            lines = snapshot.lines,
            isLoading = false,
            imageParameters = snapshot.imageParameters
        )
        Text(text = "${snapshot.engineParameters}")
        Text(text = "${snapshot.imageParameters}")
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
    }
}