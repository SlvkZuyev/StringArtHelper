package com.sldev.string_drawer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.sldev.string_drawer.extensions.toGrayscale


fun getBitmapResource(context: Context, resId: Int, targetW: Int, targetH: Int): Bitmap {
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    val photoW = bmOptions.outWidth
    val photoH = bmOptions.outHeight
    var scaleFactor = 1
    if (targetW > 0 || targetH > 0) {
        scaleFactor = Math.min(photoW / targetW, photoH / targetH)
    }
    bmOptions.inJustDecodeBounds = false
    bmOptions.inSampleSize = scaleFactor
    bmOptions.inPurgeable = true //Deprecated API 21


    val bitmap = BitmapFactory.decodeResource(context.resources, resId, bmOptions)
    val resized = Bitmap.createScaledBitmap(bitmap, targetW, targetH, true)

    return resized.toGrayscale()
}