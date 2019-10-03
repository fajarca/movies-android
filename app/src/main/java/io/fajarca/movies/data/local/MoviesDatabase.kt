package io.fajarca.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.movies.data.local.dao.MovieDao
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.util.Converters


@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}