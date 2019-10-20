package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.Movie

@Dao
interface CastDao {

    @Query("SELECT * FROM casts WHERE movie_id =:movieId")
    fun findAll(movieId: Long): LiveData<List<Cast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cast: Cast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(casts: List<Cast>)


}