package io.fajarca.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun setImageUrl(view : ImageView, imageUrl : String?) {
    if (imageUrl.isNullOrEmpty()) return

    val url = IMAGE_BASE_URL + imageUrl
    Glide.with(view.context)
        .load(url)
        .into(view)
}