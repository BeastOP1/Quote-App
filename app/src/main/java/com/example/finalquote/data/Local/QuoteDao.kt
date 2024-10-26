package com.example.finalquote.data.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalquote.domain.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuote(quote: Quote)


    @Delete
    suspend fun deleteQuote(data: Quote)

    @Query("SELECT * FROM Quote")
    fun getAllQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM Quote WHERE quote= :quote")
    suspend fun getQuote(quote: String): Quote?


}