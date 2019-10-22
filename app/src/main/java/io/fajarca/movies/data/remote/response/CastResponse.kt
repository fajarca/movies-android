package io.fajarca.movies.data.remote.response

import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("cast")
    val cast: List<Cast> = listOf()
)

data class Cast(
    @SerializedName("character")
    val character: String? = "",
    @SerializedName("credit_id")
    val creditId: String? = "",
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("profile_path")
    val profilePath: String? = ""
)