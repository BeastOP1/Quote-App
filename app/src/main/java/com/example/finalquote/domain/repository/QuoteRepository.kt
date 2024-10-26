package com.example.finalquote.domain.repository

import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {


    //api operations
    suspend fun getRemoteQuotes(category: String): List<Quote>
    suspend fun getRemoteCategory(): List<String>
    suspend fun getAllRemoteQuotes(): List<List<LocalQuote>>

    // Local operations for bookmark
    fun getLocalQuotes(): Flow<List<Quote>>
    suspend fun insertQuote(quote: Quote)
    suspend fun deleteQuote(quote: Quote)
    suspend fun getLocalQuote(category: String):Quote? //that's for share quote item
    //table 2
    fun getAllLocalQuotes(): Flow<List<LocalQuote>> // that's for all quotes that saved in db
    suspend fun addSearchQuote(quote: LocalQuote)
    suspend fun deleteSearchQuote(quote: LocalQuote)

    fun searchLocalQuotes(query: String): Flow<List<LocalQuote>> // New method for search





}