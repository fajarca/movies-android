package io.fajarca.movies.data.remote.mapper

abstract class Mapper<I, O>(private val input: I) {

    abstract fun map(input: I): O

    fun result(): O {
        return map(input)
    }
}