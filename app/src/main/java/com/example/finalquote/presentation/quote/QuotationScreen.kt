package com.example.finalquote.presentation.quote

import com.example.finalquote.R
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.finalquote.domain.model.Quote
import com.example.finalquote.presentation.bookmark.BookmarkViewModel
import com.example.finalquote.presentation.components.CustomAlertDialog
import com.example.finalquote.presentation.components.QuoteItem
import com.example.finalquote.presentation.components.QuoteTopBar
import com.example.finalquote.presentation.navigation.Destination
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomLightPink

@Composable
fun QuotationScreen(
    bookmarkViewModel: BookmarkViewModel,
    context: Context,
    navController: NavHostController,
    categoryName: String,
    quoteViewModel: QuoteViewModel,
    activityContext: Context

) {
    val quotations by quoteViewModel.quotations.collectAsState()

    val sideEffect by quoteViewModel.sideEffect.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedQuote: Quote? by remember { mutableStateOf(null) }

    LaunchedEffect(sideEffect) {

        sideEffect?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            quoteViewModel.onEvent(QuotationEvent.RemoveSideEffects)
        }
    }

    Scaffold(

        topBar = {
            QuoteTopBar(
                title = categoryName,
                colorList = listOf(CustomLightPink, CustomDarkPink),
                navigationIcon = R.drawable.arrow,
                bookmarkAction = {

                    navController.navigate(Destination.BookMarkFirst)
                }) {
                navController.navigateUp()
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 5.dp),
            contentPadding = it
        ) {

            if (quotations.isEmpty()) {
                // Show a message indicating no quotes are available
                item {
                    Text(
                        "No quotes available for this category.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(quotations.distinctBy { it.category to it.author to it.quote }) { quote ->
                    val isBooked = bookmarkViewModel.isBookmarked(quote)
                    val saves = listOf(
                        "67.2k", "5.4k", "7M", "652", "90.2k", "3.9k", "100k"
                    )
                    QuoteItem(
                        quote, isBookMarked = isBooked, onBookmarkSave = {
                            if (isBooked) {
                                showDialog = true
                                selectedQuote = quote
                            } else {
                                bookmarkViewModel.toggleBookmark(quote)
                                quoteViewModel.onEvent(QuotationEvent.InsertDeleteQuote(quote))
                            }
                        },
                        onShareCLick = {
                            val sendIntent = Intent(
                                Intent.ACTION_SEND
                            ).apply {
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Quote: ${quote.quote}\nAuthor: ${quote.author ?: "Unknown"}"
                                )
                                type = "text/plain"

                            }
                            val shareIntent = Intent.createChooser(
                                sendIntent, "Quote"
                            )
                            activityContext.startActivity(shareIntent)
                        },
                        saves = saves.iterator().toString()
                    )
                    // Ensure quote is not null
                }


            }
        }
        if (showDialog) {
            CustomAlertDialog(onConfirmed = {
                selectedQuote?.let { quote ->
                    bookmarkViewModel.toggleBookmark(quote)
                    quoteViewModel.onEvent(QuotationEvent.InsertDeleteQuote(quote))
                }
                showDialog = false
            }, onCancel = {
                showDialog = false
            })
        }

    }
}


@Composable
fun PreviewQuotationScreen() {

//    QuotationScreen(navController, categoryName)
}