package io.fajarca.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "categories")
data class Category(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "name")
    var name : String = ""

)
