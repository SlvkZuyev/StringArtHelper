package com.sldev.string_drawer.utils

import kotlinx.coroutines.*

class Looper(private val onLoop: () -> Unit, private val scope: CoroutineScope) {
    var delay: Long = 6000
    var isLooping: Boolean = false
        private set
    val looperJob = scope + SupervisorJob()

    fun startLooper() {
        if (isLooping) return
        isLooping = true
        looperJob.launch {
            while (isLooping) {
                delay(delay)
                if (isLooping) {
                    onLoop()
                }
            }
        }
    }

    fun pause() {
        isLooping = false
//        looperJob.cancel()
    }
}