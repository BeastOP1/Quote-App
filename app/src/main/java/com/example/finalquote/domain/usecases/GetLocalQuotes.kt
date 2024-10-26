package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.Quote
import com.example.finalquote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetLocalQuotes(
    private val quoteRepository: QuoteRepository
)  {
    operator fun invoke(): Flow<List<Quote>>{
        return quoteRepository.getLocalQuotes()
    }
}