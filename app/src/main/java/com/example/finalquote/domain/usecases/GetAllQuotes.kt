package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.repository.QuoteRepository


class GetAllQuotes(private val quoteRepository: QuoteRepository) {
    suspend operator fun invoke(): List<List<LocalQuote>>{
        return quoteRepository.getAllRemoteQuotes()
    }
}

//this for actual fetching quotes by api