package io.fajarca.movies.data.remote.mapper.moviedetail

import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.Category
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.remote.mapper.Mapper
import io.fajarca.movies.data.remote.response.CastResponse
import io.fajarca.movies.data.remote.response.MovieDetailsResponse

class MovieDetailMapper {

    fun mapMovieResponseToMovie(input: MovieDetailsResponse): Movie {
        return object : Mapper<MovieDetailsResponse, Movie>(input) {
            override fun map(input: MovieDetailsResponse): Movie {
                return Movie(
                    input.id,
                    input.title ?: "",
                    input.originalTitle ?: "",
                    input.overview ?: "",
                    input.backdropPath,
                    input.posterPath ?: "",
                    input.originalLanguage ?: "",
                    input.popularity,
                    input.voteCount,
                    input.voteAverage,
                    input.video,
                    input.adult,
                    input.releaseDate ?: "",
                    input.runtime
                )
            }


        }.result()
    }

    fun mapMovieResponseToCategory(input: MovieDetailsResponse): List<Category> {
        return object : Mapper<MovieDetailsResponse, List<Category>>(input) {
            override fun map(input: MovieDetailsResponse): List<Category> {
                val categories = mutableListOf<Category>()
                input.genres.forEach {
                    categories.add(Category(it.id, it.name))
                }
                return categories
            }

        }.result()
    }

    fun mapCastResponseToCast(movieId: Long, input: CastResponse): List<Cast> {
        return object : Mapper<CastResponse, List<Cast>>(input) {
            override fun map(input: CastResponse): List<Cast> {
                val casts = mutableListOf<Cast>()
                input.cast.forEach {
                    casts.add(
                        Cast(
                            movieId,
                            it.id,
                            it.character ?: "",
                            it.creditId ?: "",
                            it.name ?: "",
                            it.profilePath ?: ""
                        )
                    )
                }
                return casts
            }
        }.result()
    }
}