package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.Quote
import com.example.finalquote.domain.repository.QuoteRepository

class GetLocalQuote(
    private val quoteRepository: QuoteRepository
) {

    suspend operator fun invoke(quote: String):Quote?{
        return quoteRepository.getLocalQuote(quote)
    }
}