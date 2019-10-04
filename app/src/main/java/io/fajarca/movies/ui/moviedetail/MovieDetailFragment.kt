package io.fajarca.movies.ui.moviedetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.fajarca.movies.R
import io.fajarca.movies.base.BaseFragment
import io.fajarca.movies.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_movie_detail
    override fun getViewModelClass() = MovieDetailViewModel::class.java
    private  var movieId = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        movieId = MovieDetailFragmentArgs.fromBundle(arguments!!).movieId
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.setData(movieId)
        vm.movieDetail.observe(this, Observer {

        })
    }


}
