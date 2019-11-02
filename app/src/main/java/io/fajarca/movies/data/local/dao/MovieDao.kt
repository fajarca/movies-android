package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.data.local.entity.Movie

@Dao
abstract class MovieDao {

    @Query("SELECT * FROM movies WHERE id =:movieId")
    abstract fun findMovieById(movieId: Long): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(nowPlayings: Movie)
}