package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.data.local.entity.MovieCategoryJoin
import io.fajarca.movies.data.local.join.MovieCategory

@Dao
interface MovieCategoryDao {
    @Query("""
        SELECT movies.id as movie_id, movies.title, movies.overview, movies.backdrop_path, movies.poster_path, movies.video, movies.adult, movies.release_date, categories.id as category_id, categories.name as category_name FROM movies
       INNER JOIN movie_category_join ON movies.id  = movie_category_join.movie_id
	   INNER JOIN categories ON movie_category_join.category_id = categories.id
       WHERE  movie_category_join.movie_id = :movieId"""
    )
    fun findMovieWithCategory(movieId: Long): LiveData<List<MovieCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieCategoryJoin: MovieCategoryJoin)
}