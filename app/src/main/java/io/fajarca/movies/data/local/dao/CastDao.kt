package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.fajarca.movies.data.local.entity.Cast

@Dao
abstract class CastDao {

    @Query("SELECT * FROM casts WHERE movie_id =:movieId")
    abstract fun findAll(movieId: Long): LiveData<List<Cast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(cast: Cast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(casts: List<Cast>)

    @Query("DELETE FROM casts WHERE movie_id =:movieId")
    abstract fun deleteById(movieId: Long)

    /**
     * Execute multiple queries in single transaction
     */
    @Transaction
    open suspend fun deleteAndInsertTransaction(movieId: Long, casts: List<Cast>) {
        deleteById(movieId)
        insertAll(casts)
    }


}