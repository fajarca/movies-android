package io.fajarca.movies.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.fajarca.movies.util.ViewModelFactory
import io.fajarca.movies.util.ViewModelKey
import io.fajarca.movies.ui.home.HomeViewModel
import io.fajarca.movies.ui.moviedetail.MovieDetailViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun providesHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun providesMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}