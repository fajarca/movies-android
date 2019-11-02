package io.fajarca.movies.di.module

import dagger.Module
import dagger.Provides
import io.fajarca.movies.data.local.MoviesDatabase
import io.fajarca.movies.data.remote.mapper.moviedetail.MovieDetailMapper
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNowPlayingDao(db: MoviesDatabase) = db.nowPlayingDao()

    @Provides
    @Singleton
    fun provideMovieDao(db: MoviesDatabase) = db.movieDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: MoviesDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun provideCastDao(db: MoviesDatabase) = db.castDao()

    @Provides
    @Singleton
    fun provideMovieCategoryJoinDao(db: MoviesDatabase) = db.movieCategoryDao()

    @Provides
    @Singleton
    fun provideMovieDetailMapper() = MovieDetailMapper()
}