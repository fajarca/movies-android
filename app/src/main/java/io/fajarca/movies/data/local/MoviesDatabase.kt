package io.fajarca.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.movies.data.local.dao.*
import io.fajarca.movies.data.local.entity.*
import io.fajarca.movies.util.Converters

@Database(entities = [
    NowPlaying::class,
    Movie::class,
    Category::class,
    MovieCategoryJoin::class,
    Cast::class
], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun nowPlayingDao(): NowPlayingDao
    abstract fun movieDao(): MovieDao
    abstract fun categoryDao(): CategoryDao
    abstract fun movieCategoryDao(): MovieCategoryDao
    abstract fun castDao() : CastDao
}