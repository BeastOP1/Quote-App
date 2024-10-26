package com.example.finalquote.data.remote

import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.model.Quote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface QuoteApi {
    //
    @GET("/v3/b/67012bbce41b4d34e43d6e78?meta=false")
    suspend fun getQuotes(
        @Header("X-JSON-Path") category: String
    ) : Response<List<Quote>>

    @GET("/v3/b/67012bbce41b4d34e43d6e78?meta=false")
    @Headers("X-JSON-Path:quotes..category")
    suspend fun getCategory() : Response<List<String>>


    @GET("/v3/b/67012bbce41b4d34e43d6e78?meta=false")
    @Headers("X-JSON-Path:quotes")
    suspend fun getAllQuotes(): Response<List<List<LocalQuote>>>
}