package io.fajarca.movies.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import io.fajarca.movies.R
import io.fajarca.movies.base.BaseFragment
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.databinding.FragmentHomeBinding
import io.fajarca.movies.util.extensions.plusAssign
import io.fajarca.movies.vo.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    NowPlayingPagerAdapter.onNowPlayingPressedListener {

    companion object {
        private const val SWIPE_INTERVAL = 8000L
    }

    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java

    private lateinit var viewPager: ViewPager
    private lateinit var nowPlayingAdapter: NowPlayingPagerAdapter
    private val compositeDisposable = CompositeDisposable()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNowPlayingBanner()
        initBannerSwipeScheduler()

        vm.nowPlaying.observe(this, Observer { data -> setupNowPlaying(data) })

    }

    private fun setupNowPlaying(data: Result<List<Movie>>?) {
        data?.let {
            when(it.status) {
                Result.Status.LOADING -> {
                    binding.stateView.showLoading()
                    Timber.v("[Now playing] : Loading")
                }
                Result.Status.ERROR -> {
                    binding.stateView.hideLoading()
                    Timber.v("[Now playing] : Error}")
                }
                Result.Status.SUCCESS -> {
                    binding.stateView.hideLoading()
                    refreshBanner(it.data ?: emptyList())
                }
                else -> {

                }
            }
        }
    }

    private fun initBannerSwipeScheduler() {
        compositeDisposable += Observable.interval(SWIPE_INTERVAL, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val currentViewpagerPosition = viewPager.currentItem
                val headlineBannerSize = nowPlayingAdapter.count
                if (currentViewpagerPosition < headlineBannerSize - 1) {
                    viewPager.setCurrentItem(currentViewpagerPosition + 1, true)
                } else {
                    viewPager.setCurrentItem(0, true)
                }
            }

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
