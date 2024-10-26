package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.Quote
import com.example.finalquote.domain.repository.QuoteRepository

class GetRemoteQuote(
    private val quoteRepository: QuoteRepository
) {

    suspend  operator fun invoke(category:String): List<Quote>{
        return quoteRepository.getRemoteQuotes(category)
    }

}