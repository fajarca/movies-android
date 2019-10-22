package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.data.local.entity.MovieGenreJunction
import io.fajarca.movies.data.local.join.MovieWithGenres

@Dao
abstract class MovieGenreJunctionDao {
    @Query("SELECT * FROM MOVIES WHERE id =:movieId")
    abstract fun findMovieWithCategories(movieId: Long): LiveData<MovieWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(movieCategoryJoin: List<MovieGenreJunction>)
}