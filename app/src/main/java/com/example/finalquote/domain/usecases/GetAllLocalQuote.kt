package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllLocalQuote(
    private val quoteRepository: QuoteRepository
) {
    operator fun invoke(): Flow<List<LocalQuote>>{
        return quoteRepository.getAllLocalQuotes()
    }
}

//that's for fetching all the quotes in local db that directly saved in db