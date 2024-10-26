package com.example.finalquote.presentation.bookmark

import com.example.finalquote.domain.model.Quote

data class BookmarkState(
    val quotes: List<Quote> = emptyList(),
    val bookmarkedQuotes: Set<Quote> = emptySet()
)