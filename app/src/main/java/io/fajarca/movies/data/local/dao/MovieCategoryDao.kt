package io.fajarca.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.local.entity.MovieCategoryJoin

@Dao
interface MovieCategoryDao {
    @Query("""
      SELECT * FROM movies
       INNER JOIN movie_category_join ON 
        movies.id  = movie_category_join.movie_id
       WHERE  movie_category_join.movie_id =:movieId  
        """
    )
    fun getMovieCategory(movieId: Long): LiveData<Array<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieCategoryJoin: MovieCategoryJoin)
}