package com.example.finalquote.domain.usecases

data class QuoteUseCase(
    val getRemoteQuote: GetRemoteQuote,
    val getRemoteCategory: GetRemoteCategory,
    val getLocalQuotes: GetLocalQuotes,
    val insertQuote: InsertQuote,
    val deleteQuote: DeleteQuote,
    val getLocalQuote: GetLocalQuote,
    val addSearchQuote : AddSearchQuote,
    val deleteSearchQuote: DeleteSearchQuote,
    val getAllLocalQuote : GetAllLocalQuote,//1 db
    val getSearchLocalQuotes : GetSearchLocalQuotes,//2
    val getAllQuotes : GetAllQuotes,//actual api

)