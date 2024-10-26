package com.example.finalquote.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "LocalQuote",
    indices = [Index(value =["quote"], unique = true)]
)
data class LocalQuote(
    val category: String,
    val author: String?,
    @PrimaryKey
    val quote: String
)
