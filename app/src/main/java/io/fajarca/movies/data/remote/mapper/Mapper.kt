package io.fajarca.movies.data.remote.mapper

abstract class Mapper<I, O> {
    abstract fun map(input : I) : O
}