package io.fajarca.movies.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import io.fajarca.movies.R
import io.fajarca.movies.util.extensions.toLocalizedDatetimeFormat

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) return

    val url = IMAGE_BASE_URL + imageUrl

    val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .centerCrop()

    Glide.with(view.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .thumbnail(0.2f)
        .apply(requestOptions)
        .into(view)
}

@BindingAdapter("loadPortraitImage")
fun loadPortraitImage(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) return

    val url = IMAGE_BASE_URL_200 + imageUrl

    val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_placeholder)
        .centerCrop()

    Glide.with(view.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .thumbnail(0.2f)
        .apply(requestOptions)
        .into(view)
}

@BindingAdapter("toLocalizedDatetime")
fun toLocalizedDatetimeFormat(view: TextView, date: String?) {
    view.text = date.toLocalizedDatetimeFormat()
}