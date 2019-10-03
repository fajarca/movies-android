package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.data.local.entity.Movie

@Dao
interface MovieDao {
/*
    @Query("SELECT * FROM movies ORDER BY release_date DESC")
    fun findAllNowPlaying() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)*/


    @Query("SELECT * FROM movies ORDER BY release_date DESC")
    fun findAllNowPlaying() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

}