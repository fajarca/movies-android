package io.fajarca.movies.data.remote.mapper.moviedetail

import io.fajarca.movies.data.local.entity.Category
import io.fajarca.movies.data.local.entity.Movie
import io.fajarca.movies.data.remote.mapper.Mapper
import io.fajarca.movies.data.remote.response.Genre
import io.fajarca.movies.data.remote.response.MovieDetailsResponse

class MovieResponseToCategoryMapper : Mapper<MovieDetailsResponse,  List<Category>>() {

    override fun map(input: MovieDetailsResponse): List<Category> {
        val categories = mutableListOf<Category>()
        input.genres.forEach {
            categories.add(Category(it.id, it.name))
        }
        return categories
    }

}