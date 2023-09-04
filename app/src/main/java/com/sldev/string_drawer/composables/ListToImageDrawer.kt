package com.sldev.string_drawer.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun ListToImage(list: List<List<Int>>, modifier: Modifier = Modifier) {
    Column(modifier) {
        for (row in list) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                for (item in row) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(item))
                    ) {
                        Text("t")
                    }
                }
            }
        }
    }
}