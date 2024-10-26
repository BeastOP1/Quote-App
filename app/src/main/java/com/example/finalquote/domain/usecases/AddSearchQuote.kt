package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.repository.QuoteRepository


class AddSearchQuote(
    private val quoteRepository: QuoteRepository
) {
    suspend operator fun invoke(quote: LocalQuote){
        return quoteRepository.addSearchQuote(quote = quote)
    }
}