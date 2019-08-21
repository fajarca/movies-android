package io.fajarca.movies.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import io.fajarca.movies.R
import io.fajarca.movies.base.BaseFragment
import io.fajarca.movies.databinding.FragmentHomeBinding
import io.fajarca.movies.common.Result
import timber.log.Timber

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getNowPlaying()

        vm.nowPlaying.observe(this, Observer {
            it?.let {
                when(it) {
                    is Result.Loading -> {

                    }
                    is Result.HasData -> {

                    }
                    is Result.NoData -> {

                    }

                }
            }
        })

    }
}
