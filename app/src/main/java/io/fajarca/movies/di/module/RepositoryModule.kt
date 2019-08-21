package io.fajarca.movies.di.module

import dagger.Module
import dagger.Provides
import io.fajarca.movies.db.MoviesDatabase
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieDao(db: MoviesDatabase) = db.movieDao()

}