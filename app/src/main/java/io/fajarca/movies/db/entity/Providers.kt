package io.fajarca.movies.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "providers")
data class Providers(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "username")
    val username : String,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "address")
    val address: String?,

    @ColumnInfo(name = "telephone")
    val telephone: String?,

    @ColumnInfo(name = "city_id")
    val cityId: Int?,

    @ColumnInfo(name = "city_note")
    val cityNote: String?,

    @ColumnInfo(name = "provider_type")
    val providerType: Int?,

    @ColumnInfo(name = "provider_type_label")
    val providerTypeLabel : String?,

    @ColumnInfo(name = "latitude")
    val latitude: Double?,

    @ColumnInfo(name = "longitude")
    val longitude: Double?,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean? = false,

    val distance : Double = 0.0

)