package com.sldev.string_drawer.extensions

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import androidx.compose.ui.graphics.Color


fun Bitmap.to2dList(): List<List<Int>> {
    val result = MutableList(width) {
        MutableList(height) { 0 }
    }

    for (y in 0 until height) {
        for (x in 0 until width) {
            val pixel = getPixel(x, y)
            result[x][y] = 255 - (Color(pixel).red * 255).toInt()
        }
    }

    return result
}

fun Bitmap.toGrayscale(): Bitmap {
    val bmpGrayscale = Bitmap.createBitmap(
        width, height, Bitmap.Config.RGB_565
    )
    val c = android.graphics.Canvas(bmpGrayscale)
    val paint = android.graphics.Paint()
    val cm = ColorMatrix()
    cm.setSaturation(0f)
    val f = ColorMatrixColorFilter(cm)
    paint.colorFilter = f
    c.drawBitmap(this, 0f, 0f, paint)
    return bmpGrayscale
}