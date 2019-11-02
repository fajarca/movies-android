package io.fajarca.movies.data.remote.mapper.moviedetail

import io.fajarca.movies.data.local.entity.Cast
import io.fajarca.movies.data.local.entity.Genre
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.local.entity.MovieGenreJunction
import io.fajarca.movies.data.remote.mapper.Mapper
import io.fajarca.movies.data.remote.response.CastResponse
import io.fajarca.movies.data.remote.response.MovieDetailsResponse
import io.fajarca.movies.data.remote.response.MovieGenre

class MovieDetailMapper {

    fun mapMovieResponseToMovie(input: MovieDetailsResponse): Movie {
        return object : Mapper<MovieDetailsResponse, Movie>(input) {
            override fun map(input: MovieDetailsResponse): Movie {
                return Movie(
                    input.id,
                    input.title,
                    input.originalTitle,
                    input.overview ?: "",
                    input.backdropPath,
                    input.posterPath ?: "",
                    input.originalLanguage,
                    input.popularity,
                    input.voteCount,
                    input.voteAverage,
                    input.video,
                    input.adult,
                    input.releaseDate,
                    input.runtime ?: 0
                )
            }
        }.result()
    }

    fun mapMovieResponseToCategory(input: MovieDetailsResponse): List<Genre> {
        return object : Mapper<MovieDetailsResponse, List<Genre>>(input) {
            override fun map(input: MovieDetailsResponse): List<Genre> {
                val categories = mutableListOf<Genre>()
                input.genres.forEach {
                    categories.add(Genre(it.id, it.name))
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
                            it.character,
                            it.creditId,
                            it.name ?: "",
                            it.profilePath ?: ""
                        )
                    )
                }
                return casts
            }
        }.result()
    }

    fun mapGenreToMovieCategory(movieId: Long, genres: List<MovieGenre>): List<MovieGenreJunction> {
        return object : Mapper<List<MovieGenre>, List<MovieGenreJunction>>(genres) {
            override fun map(input: List<MovieGenre>): List<MovieGenreJunction> {
                val genres = mutableListOf<MovieGenreJunction>()
                input.forEach {
                    genres.add(MovieGenreJunction(movieId, it.id))
                }
                return genres
            }
        }.result()
    }
}