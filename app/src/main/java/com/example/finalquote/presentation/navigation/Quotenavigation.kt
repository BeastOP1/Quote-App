package com.example.finalquote.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen{
    @Serializable
    data object Home: Screen()

    @Serializable
    data object Category: Screen()

    @Serializable
    data object Quotation: Screen()

    @Serializable
    data object BookMark : Screen()



}



sealed class Destination{
    @Serializable
    data object  HomeFirst: Destination()
    @Serializable
    data object  HomeSecond: Destination()

    @Serializable
    data object  CategoryFirst: Destination()

    @Serializable
    data class QuotationFirst(val category: String) : Destination()
    @Serializable
    data class QuotationSecond(val category: String) : Destination()

    @Serializable
    data object  BookMarkFirst : Destination()

}


