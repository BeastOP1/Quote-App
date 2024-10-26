package com.example.finalquote.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "Quote",
    indices = [Index(value =["quote"], unique = true)]
)
data class Quote(

    @SerializedName("category")
    val category: String,

    @SerializedName("author")
    val author: String?,
    @PrimaryKey
    val quote: String
)