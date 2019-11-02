package io.fajarca.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.movies.data.local.dao.GenreDao
import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.local.dao.MovieGenreJunctionDao
import io.fajarca.movies.data.local.dao.NowPlayingDao
import io.fajarca.movies.data.local.dao.CastDao
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.local.entity.Genre
import io.fajarca.movies.data.local.entity.MovieGenreJunction
import io.fajarca.movies.data.local.entity.Cast
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
    abstract fun castDao(): CastDao
}