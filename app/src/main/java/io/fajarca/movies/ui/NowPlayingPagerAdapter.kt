package io.fajarca.movies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.fajarca.movies.R
import io.fajarca.movies.db.entity.Movie

class NowPlayingPagerAdapter(var movies: List<Movie>, val context: Context, var listener: onNowPlayingPressedListener) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount() = movies.size

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val view = obj as View
        container.removeView(view)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_now_playing, container, false)

        val ivPoster = view.findViewById<ImageView>(R.id.ivPoster)
        val cardView = view.findViewById<CardView>(R.id.cardViewBanner)
        val posterImage = "https://image.tmdb.org/t/p/w400/"+movies[position].backdropPath


        val options = RequestOptions
            .fitCenterTransform()

        Glide.with(context)
            .load(posterImage)
            .apply(options)
            .thumbnail(0.1f)
            .into(ivPoster)

        cardView.setOnClickListener {
            listener.onNowPlayingPressed(movies[position], position)
        }

        container.addView(view)

        return view
    }

    fun refreshNowPlaying(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }


    interface onNowPlayingPressedListener {
        fun onNowPlayingPressed(banner: Movie, position: Int)
    }

}
