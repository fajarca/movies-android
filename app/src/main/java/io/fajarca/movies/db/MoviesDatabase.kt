package io.fajarca.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.movies.db.dao.MovieDao
import io.fajarca.movies.db.entity.Movie
import io.fajarca.movies.util.Converters


@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}