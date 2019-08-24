package io.fajarca.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.base.BaseDao
import io.fajarca.movies.db.entity.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
abstract class MovieDao : BaseDao<Movie> {


    @Query("SELECT * FROM movies ORDER BY release_date DESC")
    abstract fun findAllNowPlaying() : Observable<List<Movie>>


}