package io.fajarca.movies.di.module

import dagger.Module
import dagger.Provides
import io.fajarca.movies.data.local.MoviesDatabase
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieDao(db: MoviesDatabase) = db.movieDao()

}