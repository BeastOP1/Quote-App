package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.Quote
import com.example.finalquote.domain.repository.QuoteRepository

class DeleteQuote(
    private val quoteRepository: QuoteRepository
) {
    suspend operator fun invoke(quote: Quote){
        quoteRepository.deleteQuote(quote)
    }
}