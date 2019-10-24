package io.fajarca.movies.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.fajarca.movies.data.MoviesRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class MovieDetailViewModelTest {

    @Mock
    lateinit var repository : MoviesRepository

    lateinit var viewModel: MovieDetailViewModel

    //Run LiveData synchronously
    @get:Rule
    var rule  = InstantTaskExecutorRule()

    private val movieId = 120L


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieDetailViewModel(repository)
    }


    @Test
    fun givenValidMovieId_WhenMovieDetailRequested_ThenFetchData() {
        viewModel.initData(movieId)

        viewModel.movieDetail.observeForever{}
        verify(repository).fetchMovieDetail(movieId)
    }

}