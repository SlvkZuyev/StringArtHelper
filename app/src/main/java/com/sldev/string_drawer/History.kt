//package com.sldev.string_drawer
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Divider
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.unit.dp
//import com.sldev.string_drawer.models.Line
//import com.sldev.string_drawer.models.Parameters
//import com.sldev.string_drawer.models.Snapshot
//
//
//val savedData = mutableListOf<Snapshot>()
//
//
//@Composable
//fun HistoryScreen() {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        items(savedData) {
//            SnapshotItem(snapshot = it)
//        }
//    }
//}
//
//@Composable
//fun SnapshotItem(modifier: Modifier = Modifier, snapshot: Snapshot) {
//    Column(modifier) {
//        LinesDrawingSurface(
//            lines = snapshot.lines,
//            modifier = Modifier
//                .fillMaxWidth(1f)
//                .height(440.dp)
//                .padding(16.dp)
//                .rotate(0f),
//            strokeWidth = snapshot.params.strokeWidth,
//            alpha = snapshot.params.alpha
//        )
//        Text(
//            modifier = Modifier.padding(horizontal = 16.dp),
//            text = "Resource: ${snapshot.params.resourceName}"
//        )
//        Text(
//            modifier = Modifier.padding(16.dp),
//            text = "Gray delta: ${snapshot.params.grayDelta} " +
//                    "Dark modifier: ${snapshot.params.darkModifier} " +
//                    "Lines count: ${snapshot.params.linesCount} " +
//                    "Stroke width: ${snapshot.params.strokeWidth} " +
//                    "Alpha: ${snapshot.params.alpha} "
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Divider()
//    }
//}
//
//
//class HistoryActivity() : ComponentActivity() {
//    override fun onPostCreate(savedInstanceState: Bundle?) {
//        super.onPostCreate(savedInstanceState)
//        setContent {
//            HistoryScreen()
//        }
//    }
//}