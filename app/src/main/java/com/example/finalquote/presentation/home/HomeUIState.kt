package com.example.finalquote.presentation.home


import com.example.finalquote.domain.model.LocalQuote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class HomeUIState(
    val notificationEnabled: Boolean = true,
    val searchQuery: String = "",
    val quotes: Flow<List<LocalQuote>> = flowOf(emptyList())
)
