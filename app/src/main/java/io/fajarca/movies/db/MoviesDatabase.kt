package io.fajarca.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.fajarca.movies.db.dao.ProvidersDao
import io.fajarca.movies.db.entity.Providers


@Database(entities = [Providers::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun providersDao(): ProvidersDao
}