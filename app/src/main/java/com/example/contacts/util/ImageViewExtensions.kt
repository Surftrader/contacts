package com.example.contacts.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String, useGlide: Boolean = true) {
    if (useGlide) {
        Glide.with(context)
            .load(url)
            .error(R.drawable.ic_error)
            .into(this)
    } else {
        Picasso.get()
            .load(url)
            .error(R.drawable.ic_error)
            .into(this)
    }
}
