package com.example.finalquote.presentation.quote

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalquote.domain.model.Quote
import com.example.finalquote.domain.usecases.QuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteUseCase: QuoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _quotations = MutableStateFlow<List<Quote>>(emptyList())
    val quotations: StateFlow<List<Quote>> = _quotations.asStateFlow()

    private val _sideEffects = MutableStateFlow<String?>(null)
    val sideEffect: StateFlow<String?> = _sideEffects.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            val category = savedStateHandle.get<String>("category") ?: "Motivation"
            getQuote(category)
        }

    }

    fun onEvent(event: QuotationEvent) {

        when (event) {
            is QuotationEvent.InsertDeleteQuote -> {
                viewModelScope.launch {
                    try {

                        val quotes = quoteUseCase.getLocalQuote(event.quote.quote)
                        if (quotes != null) {
                            deleteQuote(event.quote)
                        }
                        else{
                            insertQuote(event.quote)
                        }

                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

            QuotationEvent.RemoveSideEffects -> {
                _sideEffects.value = null
            }
        }
    }

    private suspend fun getQuote(category: String) {
        Log.d("Quote", "Fetching quotes for category: $category")

        try {
            val quotes = quoteUseCase.getRemoteQuote(category)
            _quotations.emit(quotes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private suspend fun deleteQuote(quote: Quote) {
        try {

            quoteUseCase.deleteQuote(quote)
            _sideEffects.value = "Quote Deleted"
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffects.value = "Error Deleting Quote"
        }
    }

    private suspend fun insertQuote(quote: Quote) {
        try {

            quoteUseCase.insertQuote(quote)
            _sideEffects.value = "Quote Saved In BookMark"
        } catch (e: Exception) {
            e.printStackTrace()
            _sideEffects.value = "Error Saving Quote"
        }
    }


}