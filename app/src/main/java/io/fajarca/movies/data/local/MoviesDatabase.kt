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
    Genre::class,
    MovieGenreJunction::class,
    Cast::class
], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun nowPlayingDao(): NowPlayingDao
    abstract fun movieDao(): MovieDao
    abstract fun categoryDao(): GenreDao
    abstract fun movieCategoryDao(): MovieGenreJunctionDao
    abstract fun castDao() : CastDao
}