package com.example.assignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.size.Scale
import com.example.assignment.R


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageByUrl(imageView: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            imageView.load(url) {
                crossfade(true)
                scale(Scale.FIT)
                error(R.drawable.broken_image)
                placeholder(R.drawable.placeholder_for_missing_posters)
            }
        } else {
            imageView.load(R.drawable.broken_image) {
                crossfade(true)
                placeholder(R.drawable.placeholder_for_missing_posters)
            }

        }


    }


}