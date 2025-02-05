package com.example.contacts.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.contacts.R

class GlideDownloader(context: Context) : ImageDownloader(context) {
    override fun downloadImage(url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .error(R.drawable.ic_error)
            .into(imageView)
    }
}
