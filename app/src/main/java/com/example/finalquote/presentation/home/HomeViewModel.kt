package com.example.finalquote.presentation.home


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.usecases.QuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quoteUseCase: QuoteUseCase
) : ViewModel() {


    private val _searchState = MutableStateFlow(HomeUIState())
    val searchState: StateFlow<HomeUIState> = _searchState


    fun onEvent(event: HomeUIEvent) {
        when (event) {

            is HomeUIEvent.UpdateSearchQuery -> {
                _searchState.value = _searchState.value.copy(
                    searchQuery = event.searchQuery
                )
                // Trigger the search as the query is updated

                performSearch(event.searchQuery)
            }

            is HomeUIEvent.SearchQuote -> {
                // Optionally perform search again, or handle differently

                performSearch(_searchState.value.searchQuery)
//
            }

            is HomeUIEvent.EnabledNotification -> {
                _searchState.value = _searchState.value.copy(
                    notificationEnabled = event.enabled
                )
            }
        }
    }

    init {
        //initial fetch All the Quotes
        fetchRemoteQuotes()

    }
private fun fetchRemoteQuotes() {
    viewModelScope.launch {
        // Collect existing quotes from the local database
        val existingQuotesList = quoteUseCase.getAllLocalQuote().firstOrNull() ?: emptyList()

        if (existingQuotesList.isEmpty()) {
            // Only fetch from remote if there are no local quotes
            val quotesList = quoteUseCase.getAllQuotes()

            // Manually flatten the list
            val flatList = mutableListOf<LocalQuote>()
            quotesList.forEach { innerList ->
                flatList.addAll(innerList)
            }

            // Insert the flattened list into the local database
            flatList.forEach { quote ->
                try {
                    quoteUseCase.addSearchQuote(quote) // Assuming this handles duplicates correctly
                } catch (e: Exception) {
                    Log.e("Quote", "Error adding quote: ${e.message}")
                }
            }

            // Create a flow from the new quotes
            val quotesFlow: Flow<List<LocalQuote>> = flow {
                emit(flatList)
            }

            _searchState.value = _searchState.value.copy(quotes = quotesFlow)
        } else {
            // If there are existing quotes, just create a flow from them
            val quotesFlow: Flow<List<LocalQuote>> = flow {
                emit(existingQuotesList)
            }
            _searchState.value = _searchState.value.copy(quotes = quotesFlow)
        }
    }
}





    private fun performSearch(query: String) {

        viewModelScope.launch {
            quoteUseCase.getSearchLocalQuotes("%$query").collect { quotes ->
                _searchState.value = _searchState.value.copy(quotes = flowOf(quotes))
            }
        }
    }


}