package io.fajarca.movies.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.databinding.ItemNowPlayingBinding

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

        val binding = ItemNowPlayingBinding.inflate(LayoutInflater.from(context), container, false)

        binding.nowPlaying = nowPlayings[position]

        binding.tvCounter.text = "${position + 1}/${nowPlayings.size}"
        binding.cardViewBanner.setOnClickListener {
            listener.onNowPlayingPressed(nowPlayings[position], position)
        }

        container.addView(binding.root)

        return binding.root
    }

    fun refreshNowPlaying(nowPlayings: List<NowPlaying>) {
        this.nowPlayings = nowPlayings
        notifyDataSetChanged()
    }


    interface onNowPlayingPressedListener {
        fun onNowPlayingPressed(banner: NowPlaying, position: Int)
    }

}
