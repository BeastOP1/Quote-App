package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.repository.QuoteRepository


class DeleteSearchQuote(
    private val quoteRepository: QuoteRepository
) {

    suspend operator fun invoke(quote: LocalQuote){
        return quoteRepository.deleteSearchQuote(quote)
    }
}