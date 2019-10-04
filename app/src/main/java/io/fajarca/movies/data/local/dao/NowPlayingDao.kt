package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.data.local.entity.NowPlaying

@Dao
interface NowPlayingDao {

    @Query("SELECT * FROM nowPlayings ORDER BY release_date DESC")
    fun findAllNowPlaying() : LiveData<List<NowPlaying>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nowPlayings: List<NowPlaying>)

}