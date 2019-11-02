package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.fajarca.movies.data.local.entity.NowPlaying

@Dao
abstract class NowPlayingDao {

    @Query("SELECT * FROM now_playings ORDER BY release_date DESC")
    abstract fun findAllNowPlaying(): LiveData<List<NowPlaying>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(nowPlayings: List<NowPlaying>)

    @Query("DELETE FROM now_playings")
    abstract fun deleteAll()

    /**
     * Execute multiple queries in single transaction
     */
    @Transaction
    open suspend fun deleteAndInsertInTransaction(nowPlayings: List<NowPlaying>) {
        deleteAll()
        insertAll(nowPlayings)
    }
}