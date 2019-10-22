package io.fajarca.movies.data.remote.mapper.moviedetail

import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.local.entity.MovieGenreJunction
import io.fajarca.movies.data.remote.mapper.Mapper
import io.fajarca.movies.data.remote.response.CastResponse
import io.fajarca.movies.data.remote.response.Genre
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

    fun mapMovieResponseToCategory(input: MovieDetailsResponse): List<io.fajarca.movies.data.local.entity.Genre> {
        return object : Mapper<MovieDetailsResponse, List<io.fajarca.movies.data.local.entity.Genre>>(input) {
            override fun map(input: MovieDetailsResponse): List<io.fajarca.movies.data.local.entity.Genre> {
                val categories = mutableListOf<io.fajarca.movies.data.local.entity.Genre>()
                input.genres.forEach {
                    categories.add(io.fajarca.movies.data.local.entity.Genre(it.id, it.name))
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

    fun mapGenreToMovieCategory(movieId: Long, genres: List<Genre>): List<MovieGenreJunction> {
        return object : Mapper<List<Genre>, List<MovieGenreJunction>>(genres) {
            override fun map(input: List<Genre>): List<MovieGenreJunction> {
                val genres = mutableListOf<MovieGenreJunction>()
                input.forEach {
                    genres.add(MovieGenreJunction(movieId, it.id))
                }
                return genres
            }

        }.result()
    }
}