package com.sldev.string_drawer.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


@Composable
fun DescriptionSlider(
    description: String,
    value: Float,
    onChange: (Float) -> Unit,
    minValue: Float,
    maxValue: Float,
    steps: Int,
    stepValue: Float = 1f,
) {
    Column {
        ValueSection(
            description = description,
            value = value,
            onIncrease = { onChange(value + stepValue) },
            onDecrease = { onChange(value - stepValue) })
        Slider(
            value = value,
            onValueChange = { onChange(it) },
            valueRange = (minValue..maxValue),
            steps = steps,

        )
    }
}

@Composable
fun ValueSection(
    description: String,
    value: Float,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(description)
        IconButton(onClick = onDecrease) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
        }
        Text(value.toString())
        IconButton(onClick = onIncrease) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}