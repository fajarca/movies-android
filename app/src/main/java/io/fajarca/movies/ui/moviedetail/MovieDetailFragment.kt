package io.fajarca.movies.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.fajarca.movies.R
import io.fajarca.movies.base.BaseFragment
import io.fajarca.movies.data.local.join.MovieCategory
import io.fajarca.movies.databinding.FragmentMovieDetailBinding
import io.fajarca.movies.vo.Result

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    override fun getLayoutResourceId(): Int = R.layout.fragment_movie_detail
    override fun getViewModelClass() = MovieDetailViewModel::class.java
    private var movieId = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        movieId = MovieDetailFragmentArgs.fromBundle(arguments!!).movieId
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.setData(movieId)
        vm.movieDetail.observe(this, Observer { subscribeMovieDetail(it) })
    }

    private fun subscribeMovieDetail(it: Result<List<MovieCategory>>) {
        it.let {
            when (it.status) {
                Result.Status.LOADING -> {
                    binding.stateView.showLoading()
                }
                Result.Status.ERROR -> {
                    binding.stateView.hideLoading()
                }
                Result.Status.SUCCESS -> {
                    displayMovieDetails(it.data)
                    binding.stateView.hideLoading()
                }
            }
        }
    }

    private fun displayMovieDetails(data: List<MovieCategory>?) {
        data?.let {
            if (data.isNotEmpty()) {
                val movie = data[0]
                binding.movie = movie
                displayGenres(data)
                displayRating(movie)
                binding.tvAdult.text = if (movie.adult) "17+" else "All Age"
                binding.tvRuntime.text = "${movie.runtime} minutes"
            }
        }
    }

    private fun displayGenres(movieWithCategories: List<MovieCategory>) {
        var genres = ""

        val size = movieWithCategories.size

        for ((i, movie) in movieWithCategories.withIndex()) {
            if (i == size - 1) {
                genres += movie.categoryName
            } else {
                genres += movie.categoryName + ", "
            }
        }

        binding.tvGenre.text = genres
    }

    private fun displayRating(movie: MovieCategory) {
        binding.tvRating.text = "${movie.voteAverage}/10"
        binding.tvRatingCount.text = "${movie.voteCount}"
    }
}
