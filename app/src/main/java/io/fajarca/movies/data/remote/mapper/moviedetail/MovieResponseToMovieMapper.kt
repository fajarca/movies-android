package io.fajarca.movies.data.remote.mapper.moviedetail

import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.remote.mapper.Mapper
import io.fajarca.movies.data.remote.response.MovieDetailsResponse

class MovieResponseToMovieMapper : Mapper<MovieDetailsResponse, Movie>() {

    override fun map(input: MovieDetailsResponse): Movie {
        return Movie(
            input.id,
            input.title,
            input.originalTitle,
            input.overview,
            input.backdropPath,
            input.posterPath,
            input.originalLanguage,
            input.popularity,
            input.voteCount,
            input.voteAverage,
            input.video,
            input.adult,
            input.releaseDate,
            input.runtime
        )
    }
}