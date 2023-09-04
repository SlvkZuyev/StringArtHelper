package com.sldev.string_drawer.ui.screens.history_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            HistoryScreenContent()
        }
    }
}