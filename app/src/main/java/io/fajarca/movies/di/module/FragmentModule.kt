package io.fajarca.movies.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.movies.ui.HomeFragment
import io.fajarca.movies.ui.moviedetail.MovieDetailFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment


    @ContributesAndroidInjector
    abstract fun contributesMovieDetailFragment(): MovieDetailFragment
}