package com.example.contacts.util

import android.content.Context
import android.widget.ImageView
import com.example.contacts.R
import com.squareup.picasso.Picasso

class PicassoDownloader(context: Context) : ImageDownloader(context) {
    override fun downloadImage(url: String, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .error(R.drawable.ic_error)
            .into(imageView)
    }
}
