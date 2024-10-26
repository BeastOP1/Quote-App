package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetSearchLocalQuotes(
    private val quoteRepository: QuoteRepository
) {
    operator fun invoke(query: String) : Flow<List<LocalQuote>>{
        return quoteRepository.searchLocalQuotes(query)
    }
}
//this one fro search