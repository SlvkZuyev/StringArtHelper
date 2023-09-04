package com.sldev.string_drawer.utils

import android.content.Context
import android.graphics.Bitmap
import com.sldev.string_drawer.ImageRadius
import com.sldev.string_drawer.ImageToRender
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BitmapProvider @Inject constructor(@ApplicationContext private val context: Context) {
    fun getBitmap(): Bitmap {
        return getBitmapResource(context, ImageToRender, ImageRadius * 2, ImageRadius * 2)
    }
}