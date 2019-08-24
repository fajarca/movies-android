package io.fajarca.movies.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import io.fajarca.movies.R
import io.fajarca.movies.base.BaseFragment
import io.fajarca.movies.common.Result
import io.fajarca.movies.databinding.FragmentHomeBinding
import io.fajarca.movies.db.entity.Movie
import java.util.*


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    NowPlayingPagerAdapter.onNowPlayingPressedListener {


    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java

    private lateinit var viewPager: ViewPager
    private lateinit var nowPlayingAdapter: NowPlayingPagerAdapter
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNowPlaying()

        vm.getNowPlaying()


        vm.nowPlaying.observe(this, Observer {
            it?.let {
                when(it) {
                    is Result.Loading -> {

                    }
                    is Result.HasData -> {
                        nowPlayingAdapter.refreshNowPlaying(it.data)
                        viewPager.setCurrentItem(it.data.size / 2, true)
                    }
                    is Result.NoData -> {

                    }
                    is Result.Error -> {

                    }

                }
            }
        })

    }

    private fun initNowPlaying() {
        viewPager = binding.viewpager

        viewPager.clipToPadding = false
        viewPager.pageMargin = 24
        viewPager.setPadding(48, 8, 48, 8)
        viewPager.offscreenPageLimit = 3



        nowPlayingAdapter = NowPlayingPagerAdapter(ArrayList(), activity!!, this)
        viewPager.adapter = nowPlayingAdapter
    }

    override fun onNowPlayingPressed(banner: Movie, position: Int) {

    }
}
