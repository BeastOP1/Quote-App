package com.example.finalquote.presentation.home


sealed class HomeUIEvent {
    data class UpdateSearchQuery(val searchQuery: String) : HomeUIEvent()

    data class EnabledNotification(val enabled : Boolean): HomeUIEvent()
    object  SearchQuote: HomeUIEvent()
}