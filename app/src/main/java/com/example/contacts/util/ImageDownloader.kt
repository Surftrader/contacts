package com.example.contacts.util

import android.content.Context
import android.widget.ImageView

abstract class ImageDownloader(var context: Context) {
    abstract fun downloadImage(url: String, imageView: ImageView)
}
