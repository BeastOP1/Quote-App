package com.example.finalquote.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalquote.domain.model.Quote

@Composable
fun BookmarkQuoteItem(
    modifier: Modifier,
    quotes: List<Quote>,
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = quotes.size
        ) {

            val quote = quotes[it]
//            QuoteItem(quotations = quote, onBookmarkSave = { /*TODO*/ }, isBookMarked = false, onShareCLick = {})

        }

    }

}