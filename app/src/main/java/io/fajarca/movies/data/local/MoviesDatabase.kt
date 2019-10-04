package io.fajarca.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fajarca.movies.data.local.dao.NowPlayingDao
import io.fajarca.movies.data.local.entity.NowPlaying
import io.fajarca.movies.util.Converters


@Database(entities = [NowPlaying::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun nowPlayingDao(): NowPlayingDao
}