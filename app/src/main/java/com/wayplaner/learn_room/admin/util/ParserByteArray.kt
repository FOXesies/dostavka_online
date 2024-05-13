package com.wayplaner.learn_room.admin.util

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun ByteArray.toBitmapImage(): ImageBitmap {
    return (BitmapFactory.decodeByteArray(this, 0, this.size)).asImageBitmap()
}