package com.example.finalquote.domain.usecases

import com.example.finalquote.domain.repository.QuoteRepository


class GetRemoteCategory(
    private val quoteRepository: QuoteRepository
){
    suspend operator fun invoke(): List<String>{
        return quoteRepository.getRemoteCategory()
    }
}