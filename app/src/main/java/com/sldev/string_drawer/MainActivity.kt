package com.sldev.string_drawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import com.sldev.string_drawer.data.InstructionRepository
import com.sldev.string_drawer.models.InstructionProgress
import com.sldev.string_drawer.ui.screens.image_editor_screen.ImageEditorScreen
import com.sldev.string_drawer.ui.screens.instruction_screen.InstructionsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var instructionsRepository: InstructionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var instructions: InstructionProgress? by remember { mutableStateOf(null) }
            LaunchedEffect(key1 = null) {
                instructions = instructionsRepository.getLastSavedInstructionProgress()
            }

            if (instructions != null) {
                if (instructions?.steps.isNullOrEmpty()) {
                    Navigator(screen = ImageEditorScreen())
                } else {
                    Navigator(
                        screen = InstructionsScreen(
                            steps = instructions?.steps!!,
                            currentStepIndex = instructions?.currentStepIndex!!
                        )
                    )
                }
            }
        }
    }
}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val imageRadius = 240
//        val resource = R.drawable.stock_face_white_bg_supercontrast_240
//
//        val cr = CircularGrid()
//        cr.testDots()
//
//
//        val image = getBitmapResource(this, resource, imageRadius * 2, imageRadius * 2)
//
//        var lines: List<Line> by mutableStateOf(emptyList())
//        var grayDelta by mutableStateOf(20)
//        var darkModifier by mutableStateOf(1)
//        var lightModifier by mutableStateOf(-1)
//        var nLines by mutableStateOf(1200)
//        var strokeWidth by mutableStateOf(1f)
//        var alpha by mutableStateOf(0.8f)
//        var showLoading by mutableStateOf(false)
//
//        fun collectParameters(): Parameters {
//            return Parameters(
//                resourceName = resources.getResourceName(resource),
//                linesCount = nLines,
//                strokeWidth = strokeWidth,
//                alpha = alpha,
//                grayDelta = grayDelta,
//                darkModifier = darkModifier
//            )
//        }
//
//        fun buildImage() {
//            lifecycleScope.launchWhenCreated {
//                withContext(Dispatchers.Default) {
//                    val engine = CircularLineEngine(
//                        image = image.to2dList(),
//                        grayDelta = grayDelta,
//                        overDarkModifier = darkModifier.toFloat(),
//                        lightModifier = lightModifier.toFloat(),
//                        anckersCount = 240,
//                        radius = imageRadius
//                    )
//
////                    lines = engine.getTestGetPixelsLines()
////
//                    val parameters = collectParameters()
//                    showLoading = true
//                    engine.calculateLines(nLines = nLines,
//                        onCreated = {
//                            lines = it
//                            saveToHistory(lines, parameters)
//                            showLoading = false
//                        })
//                }
//            }
//        }
//
//
//        Log.d("SlvkLog", "Lines: $lines")
//        setContent {
//            StringDrawerTheme {
//                LaunchedEffect(key1 = true) {
//                    buildImage()
//                }
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(), color = Color.White
//                ) {
////                    ListToImage(
////                        modifier = Modifier
////                            .fillMaxHeight(0.6f)
////                            .padding(16.dp)
////                            .aspectRatio(1f)
////                            .border(2.dp, Color.Red), list = image.to2dList()
////                    )
////                    Image(
////                        modifier = Modifier
////                            .fillMaxHeight(0.6f)
////                            .padding(16.dp)
////                            .aspectRatio(1f)
////                            .border(2.dp, Color.Red),
////                        bitmap = image.asImageBitmap(),
////                        contentDescription = null,
////                        contentScale = ContentScale.FillBounds
////                    )
//
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp)
//                    ) {
//                        item {
//
//                            Text(text = "Stroke width: $strokeWidth")
//                            Slider(
//                                value = strokeWidth,
//                                onValueChange = { strokeWidth = it },
//                                valueRange = 0f..5f,
//                                steps = 49
//                            )
//
//                            Text(text = "Alpha: $alpha")
//                            Slider(
//                                value = alpha,
//                                onValueChange = { alpha = it },
//                                valueRange = 0f..1f,
//                                steps = 19
//                            )
//
//                            LinesDrawingSurface(
//                                lines = lines,
//                                modifier = Modifier
//                                    .fillMaxWidth(1f)
//                                    .height(450.dp)
//                                    .padding(16.dp)
//                                    .rotate(0f),
//                                strokeWidth = strokeWidth,
//                                alpha = alpha
//                            )
//
//                            EditValueSection(
//                                description = "Gray Delta value: ",
//                                value = grayDelta,
//                                onChange = { grayDelta = it },
//                                minValue = 0,
//                                maxValue = 40
//                            )
//
//                            EditValueSection(
//                                description = "Dark Modifier value: ",
//                                value = darkModifier,
//                                onChange = { darkModifier = it },
//                                minValue = 0,
//                                maxValue = 80
//                            )
//
//                            EditValueSection(
//                                description = "Light Modifier: ",
//                                value = lightModifier,
//                                onChange = { lightModifier = it },
//                                minValue = -40,
//                                maxValue = 40
//                            )
//
//                            DescriptionSlider(
//                                description = "Number of Lines: ",
//                                value = nLines.toFloat(),
//                                onChange = { nLines = it.toInt()},
//                                minValue = 100.toFloat(),
//                                maxValue = 4000.toFloat()
//                            )
//
//                            Button(onClick = {
//                                buildImage()
//                            }) {
//                                Text(text = "Apply")
//                            }
//
//                            Button(onClick = {
//                                startActivity(
//                                    Intent(
//                                        this@MainActivity,
//                                        HistoryActivity::class.java
//                                    )
//                                )
//                            }) {
//                                Text(text = "History")
//                            }
//                        }
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp), contentAlignment = Alignment.TopCenter
//                    ) {
//                        CircularProgressIndicator(modifier = Modifier.alpha(if (showLoading) 1f else 0f))
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun IntSlider(
//    value: Int,
//    onChange: (Int) -> Unit,
//    minValue: Int,
//    maxValue: Int,
//) {
//    Slider(
//        value = value.toFloat(),
//        onValueChange = { onChange(it.toInt()) },
//        valueRange = (minValue.toFloat()..maxValue.toFloat()),
//    )
//}
//
//@Composable
//fun LinesDrawingSurface(
//    modifier: Modifier = Modifier,
//    lines: List<Line>,
//    strokeWidth: Float = 1f,
//    alpha: Float = 0.9f
//) {
//    Canvas(modifier = modifier) {
//        for (line in lines) {
//            drawLine(
//                color = Color.Black,
//                start = Offset(line.start),
//                end = Offset(line.end),
//                strokeWidth = strokeWidth,
//                alpha = alpha
//            )
//        }
//    }
//}
//
//
//
//
//@Composable
//fun ValueSection(description: String, value: Float, onIncrease: () -> Unit, onDecrease: () -> Unit) {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Text(description)
//        IconButton(onClick = onDecrease) {
//            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
//        }
//        Text(value.toString())
//        IconButton(onClick = onIncrease) {
//            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
//        }
//    }
//}