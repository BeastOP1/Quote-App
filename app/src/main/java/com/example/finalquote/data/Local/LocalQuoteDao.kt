package com.example.finalquote.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalquote.domain.model.LocalQuote
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalQuote(quote: LocalQuote)

    @Delete
    suspend fun deleteQuote(data: LocalQuote)

    @Query("SELECT * FROM LOCALQUOTE")
    fun getAllLocalQuotes(): Flow<List<LocalQuote>>

    //new1
    @Query("SELECT * FROM LOCALQUOTE WHERE quote LIKE '%' || :query || '%' COLLATE NOCASE")
    fun searchLocalQuotes(query: String): Flow<List<LocalQuote>> // New method for search

}