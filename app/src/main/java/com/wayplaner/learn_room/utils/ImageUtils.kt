package com.wayplaner.learn_room.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

class ImageUtils {
    companion object {
        fun convertInputStreamToBitmap(inputStream: InputStream): Bitmap? {
            return try {
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}