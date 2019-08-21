package io.fajarca.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.fajarca.movies.db.entity.Movie
import io.reactivex.Completable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<Movie>) : Completable


}