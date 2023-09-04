package com.sldev.string_drawer.composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sldev.string_drawer.utils.Offset
import androidx.compose.ui.graphics.Color
import com.sldev.string_drawer.ImageRadius
import com.sldev.string_drawer.models.ImageParameters
import com.sldev.string_drawer.models.Line


@Composable
fun LinesDrawingSurface(
    modifier: Modifier = Modifier,
    lines: List<Line>,
    imageParameters: ImageParameters,
    isLoading: Boolean,
    loadingProgress: Float = 0f
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)) {
            val scale = size.width / (ImageRadius * 2)
            for (line in lines) {
                drawLine(
                    color = Color.Black,
                    start = Offset(line.start, scale),
                    end = Offset(line.end, scale),
                    strokeWidth = imageParameters.strokeWidth,
                    alpha = imageParameters.alpha
                )
            }
        }
        AnimatedVisibility(visible = isLoading) {
            CircularProgressIndicator(progress = loadingProgress)
        }
    }
}