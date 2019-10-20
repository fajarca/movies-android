package io.fajarca.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.movies.data.local.dao.CategoryDao
import io.fajarca.movies.data.local.dao.MovieCategoryDao
import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.local.dao.NowPlayingDao
import io.fajarca.movies.data.local.entity.Category
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.local.entity.MovieCategoryJoin
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.util.Converters

@Database(entities = [NowPlaying::class, Movie::class, Category::class, MovieCategoryJoin::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun nowPlayingDao(): NowPlayingDao
    abstract fun movieDao(): MovieDao
    abstract fun categoryDao(): CategoryDao
    abstract fun movieCategoryDao(): MovieCategoryDao
}