package io.fajarca.movies.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.fajarca.movies.R
import io.fajarca.movies.base.BaseFragment
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.Genre
import io.fajarca.movies.data.local.join.MovieWithGenres
import io.fajarca.movies.databinding.FragmentMovieDetailBinding
import io.fajarca.movies.vo.Result

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(),
    CastAdapter.OnCastPressed {

    override fun getLayoutResourceId(): Int = R.layout.fragment_movie_detail
    override fun getViewModelClass() = MovieDetailViewModel::class.java
    private val adapter = CastAdapter(listOf(), this)
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

        initRecyclerView()
        vm.initData(movieId)
        vm.movieDetail.observe(this, Observer { subscribeMovieDetail(it) })
        vm.casts.observe(this, Observer { casts -> subscribeMovieCasts(casts) })
    }

    private fun subscribeMovieCasts(casts: Result<List<Cast>>) {
        casts.let {
            when (it.status) {
                Result.Status.LOADING -> {
                    binding.stateView.showLoading()
                }
                Result.Status.ERROR -> {
                    binding.stateView.hideLoading()
                }
                Result.Status.SUCCESS -> {
                    displayCasts(it.data ?: emptyList())
                    binding.stateView.hideLoading()
                }
            }
        }
    }

    private fun subscribeMovieDetail(it: Result<MovieWithGenres>) {
        it.let {
            when (it.status) {
                Result.Status.LOADING -> {
                    binding.stateView.showLoading()
                }
                Result.Status.ERROR -> {
                    binding.stateView.hideLoading()
                }
                Result.Status.SUCCESS -> {
                    vm.setMovieId(movieId)
                    displayMovieDetails(it.data)
                    binding.stateView.hideLoading()
                }
            }
        }
    }

    private fun displayMovieDetails(data: MovieWithGenres?) {
        data?.let {
            binding.movie = data
            displayGenres(it.genres)
            binding.tvRating.text = "${data.movie.voteAverage}/10"
            binding.tvRatingCount.text = "${data.movie.voteCount}"
            binding.tvAdult.text = if (data.movie.adult) "17+" else "All Age"
            binding.tvRuntime.text = "${data.movie.runtime} minutes"
        }
    }

    private fun displayGenres(genres: List<Genre>) {
        var genre = ""

        val size = genres.size

        for ((i, model) in genres.withIndex()) {
            if (i == size - 1) {
                genre += model.name
            } else {
                genre += model.name + ", "
            }
        }

        binding.tvGenre.text = genre
    }

    private fun displayCasts(data: List<Cast>) {
        adapter.refreshData(data)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun onCastPressed(cast: Cast) {
    }
}
