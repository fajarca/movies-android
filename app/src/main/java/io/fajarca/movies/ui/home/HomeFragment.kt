package io.fajarca.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import io.fajarca.movies.R
import io.fajarca.movies.base.BaseFragment
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.databinding.FragmentHomeBinding
import io.fajarca.movies.util.extensions.plusAssign
import io.fajarca.movies.vo.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    NowPlayingPagerAdapter.onNowPlayingPressedListener, ViewPager.OnPageChangeListener {


    companion object {
        private const val SWIPE_INTERVAL = 5000L
    }

    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java

    private lateinit var viewPager: ViewPager
    private lateinit var nowPlayingAdapter: NowPlayingPagerAdapter
    private val compositeDisposable = CompositeDisposable()
    private var lastNowPlayingPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBannerSwipeScheduler()
        initNowPlayingBanner()

        vm.nowPlaying.observe(this, Observer { data -> subscribeNowPlaying(data) })
    }

    private fun subscribeNowPlaying(data: Result<List<NowPlaying>>?) {
        data?.let {
            when (it.status) {
                Result.Status.LOADING -> {
                    binding.stateView.showLoading()
                }
                Result.Status.ERROR -> {
                    binding.stateView.hideLoading()
                }
                Result.Status.SUCCESS -> {
                    binding.stateView.hideLoading()
                    refreshBanner(it.data ?: emptyList())
                }
            }
        }
    }

    private fun initBannerSwipeScheduler() {
        compositeDisposable += Observable.interval(SWIPE_INTERVAL, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.v("Position $lastNowPlayingPosition")
                val headlineBannerSize = nowPlayingAdapter.count
                if (lastNowPlayingPosition < headlineBannerSize - 1) {
                    viewPager.setCurrentItem(lastNowPlayingPosition + 1, true)
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
        viewPager.addOnPageChangeListener(this)
    }

    private fun refreshBanner(data: List<NowPlaying>) {
        nowPlayingAdapter.refreshNowPlaying(data)
        viewPager.currentItem = lastNowPlayingPosition
    }

    override fun onNowPlayingPressed(banner: NowPlaying, position: Int) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToMovieDetail(banner.id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        lastNowPlayingPosition = position
    }
}
