package com.sldev.string_drawer.composables

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner


@Composable
fun LifecycleEffect(key: Any?, effect: (event: Lifecycle.Event) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = key) {
        (context as ComponentActivity).lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                effect(event)
            }
        })
    }
}