package com.example.finalquote.presentation.quote

import com.example.finalquote.domain.model.Quote

sealed class QuotationEvent {

    data class InsertDeleteQuote(val quote: Quote): QuotationEvent()
    object RemoveSideEffects: QuotationEvent()
}