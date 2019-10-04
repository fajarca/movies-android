package io.fajarca.movies.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import io.fajarca.movies.R
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.util.IMAGE_BASE_URL

class NowPlayingPagerAdapter(var nowPlayings: List<NowPlaying>, val context: Context, var listener: onNowPlayingPressedListener) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount() = nowPlayings.size

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val view = obj as View
        container.removeView(view)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_now_playing, container, false)

        val ivPoster = view.findViewById<ImageView>(R.id.ivPoster)
        val cardView = view.findViewById<CardView>(R.id.cardViewBanner)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val posterImage = IMAGE_BASE_URL+nowPlayings[position].backdropPath
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvCounter = view.findViewById<TextView>(R.id.tvCounter)

        tvTitle.text = nowPlayings[position].originalTitle
        tvCounter.text = "${position}/${nowPlayings.size}"

        val options = RequestOptions
            .fitCenterTransform()

        Glide.with(context)
            .load(posterImage)
            .apply(options)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.INVISIBLE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.INVISIBLE
                    return false
                }
            })
            .into(ivPoster)

        cardView.setOnClickListener {
            listener.onNowPlayingPressed(nowPlayings[position], position)
        }

        container.addView(view)

        return view
    }

    fun refreshNowPlaying(nowPlayings: List<NowPlaying>) {
        this.nowPlayings = nowPlayings
        notifyDataSetChanged()
    }


    interface onNowPlayingPressedListener {
        fun onNowPlayingPressed(banner: NowPlaying, position: Int)
    }

}
