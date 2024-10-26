package com.example.finalquote.data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.model.Quote

@Database(entities = [Quote::class, LocalQuote::class], version =5)
abstract class QuoteDataBase : RoomDatabase(){

    abstract val quoteDao: QuoteDao
    abstract val localQuoteDao: LocalQuoteDao
}