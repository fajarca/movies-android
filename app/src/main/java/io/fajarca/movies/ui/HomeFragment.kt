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

        initNowPlayingBanner()

        vm.getNowPlaying()

        vm.nowPlaying.observe(this, Observer {
            it?.let {
                when(it) {
                    is Result.Loading -> {
                        binding.stateView.showLoading()
                    }
                    is Result.HasData -> {
                        binding.stateView.showHasData()
                        refreshBanner(it.data)
                    }
                    is Result.NoData -> {
                    }
                    is Result.Error -> {
                        binding.stateView.showError(R.string.unknown_error)
                    }

                }
            }
        })

    }

    private fun initNowPlayingBanner() {
        viewPager = binding.viewpager.apply {
            clipToPadding = false
            pageMargin = 24
            setPadding(48, 8, 48, 8)
            offscreenPageLimit = 3
        }

        nowPlayingAdapter = NowPlayingPagerAdapter(emptyList(), requireActivity(), this)
        viewPager.adapter = nowPlayingAdapter
    }
    
    private fun refreshBanner(data: List<Movie>) {
        nowPlayingAdapter.refreshNowPlaying(data)
        viewPager.setCurrentItem(data.size / 2, true)
    }

    override fun onNowPlayingPressed(banner: Movie, position: Int) {

    }
}
