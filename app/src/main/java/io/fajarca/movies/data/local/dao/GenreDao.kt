package io.fajarca.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.fajarca.movies.data.local.entity.Genre

@Dao
abstract class GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(categories: List<Genre>)
}