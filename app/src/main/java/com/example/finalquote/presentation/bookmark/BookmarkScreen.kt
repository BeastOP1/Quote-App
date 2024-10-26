package com.example.finalquote.presentation.bookmark

import com.example.finalquote.R
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.finalquote.domain.model.Quote
import com.example.finalquote.presentation.components.CustomAlertDialog
import com.example.finalquote.presentation.components.CustomSavedQuoteItem
import com.example.finalquote.presentation.components.QuoteTopBar
import com.example.finalquote.presentation.quote.QuotationEvent
import com.example.finalquote.presentation.quote.QuoteViewModel
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomLightPink

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    context: Context,
    quoteViewModel: QuoteViewModel,
    bookmarkViewModel: BookmarkViewModel,
    activityContext: Context
) {

    val state by bookmarkViewModel.state.collectAsState()
    val sideEffect by quoteViewModel.sideEffect.collectAsState()

    // States for showing the dialog and storing the selected quote
    var showDialog by remember { mutableStateOf(false) }
    var selectedQuote: Quote? by remember { mutableStateOf(null) }

    // Handle side effects
    LaunchedEffect(sideEffect) {
        sideEffect?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            quoteViewModel.onEvent(QuotationEvent.RemoveSideEffects)
        }
    }

    // Scaffold for top bar and content
    Scaffold(
        topBar = {
            QuoteTopBar(
                title = "Bookmark",
                colorList = listOf(CustomLightPink, CustomDarkPink),
                navigationIcon = R.drawable.arrow,
                bookmarkAction = {
//                    navController.navigate(Destination.HomeFirst)
                }) {
                navController.navigateUp()
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { Spacer(modifier = Modifier.heightIn(5.dp)) }

            // Show "no quotes" message if the list is empty
            if (state.quotes.isEmpty()) {
                item {
                    Text("No quotes available for this category.", modifier = Modifier.padding(16.dp))
                }
            } else {
                items(state.quotes.distinct()) { quote ->
                    val isBooked = bookmarkViewModel.isBookmarked(quote)

                    // Quote item with bookmark action and sharing functionality
                    CustomSavedQuoteItem(
                        quotations = quote,
                        onBookmarkSave = {
                            showDialog = true
                            selectedQuote = quote // Set the quote to delete/bookmark
                        },
                        isBookMarked = isBooked,
                        onShareClick = {
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_TEXT, "Quote: ${quote.quote}\nAuthor: ${quote.author ?: "Unknown"}")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Quote")
                            activityContext.startActivity(shareIntent)
                        },
                        color = CustomDarkPink
                    )
                }
            }
            item { Spacer(modifier = Modifier.heightIn(5.dp)) }
        }

        // Show CustomAlertDialog when `showDialog` is true
        if (showDialog) {
            CustomAlertDialog(
                onConfirmed = {
                    selectedQuote?.let { quote ->
                        bookmarkViewModel.toggleBookmark(quote)
                        quoteViewModel.onEvent(QuotationEvent.InsertDeleteQuote(quote))
                    }
                    showDialog = false
                },
                onCancel = {
                    showDialog = false
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookmarkScreenPreview(modifier: Modifier = Modifier) {
    // You can preview this component here
}
