package com.example.finalquote.presentation.home
import com.example.finalquote.R

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.presentation.bookmark.BookmarkViewModel
import com.example.finalquote.presentation.category.CategoryViewModel
import com.example.finalquote.presentation.components.CustomHomeQuoteItem
import com.example.finalquote.presentation.components.CustomSavedQuoteItem
import com.example.finalquote.presentation.components.CustomSearchQuoteItem
import com.example.finalquote.presentation.components.QuoteTopBar
import com.example.finalquote.presentation.components.SavedCategories
import com.example.finalquote.presentation.components.SearchBarQuote
import com.example.finalquote.presentation.components.TransparentStatusBar
import com.example.finalquote.presentation.navigation.Destination
import com.example.finalquote.presentation.quote.QuoteViewModel
import com.example.finalquote.domain.reciver.todayQuote
import com.example.finalquote.presentation.components.CustomDrawerScreen
import com.example.finalquote.ui.theme.CustomBlack
import com.example.finalquote.ui.theme.CustomDarkGreen
import com.example.finalquote.ui.theme.CustomDarkOrange
import com.example.finalquote.ui.theme.CustomDarkParrot
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomDarkPurple
import com.example.finalquote.ui.theme.CustomDarkSky
import com.example.finalquote.ui.theme.CustomDarkYellow
import com.example.finalquote.ui.theme.CustomLightGreen
import com.example.finalquote.ui.theme.CustomLightOrange
import com.example.finalquote.ui.theme.CustomLightParrot
import com.example.finalquote.ui.theme.CustomLightPink
import com.example.finalquote.ui.theme.CustomLightPurple
import com.example.finalquote.ui.theme.CustomLightSky
import com.example.finalquote.ui.theme.CustomLightWhite
import com.example.finalquote.ui.theme.CustomLightYellow
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel,
    homeViewModel: HomeViewModel,
    bookmarkViewModel: BookmarkViewModel,
    viewAllClick: () -> Unit,
    activityContext : Context,
    quoteViewModel: QuoteViewModel
) {

    val categories by categoryViewModel.categories.collectAsState()


    val state by bookmarkViewModel.state.collectAsState()

    val searchState by homeViewModel.searchState.collectAsState()
    // Use SystemUiController to control system bar colors
    val searchResults = searchState.quotes.collectAsState(initial = emptyList()).value

    val colors = listOf(
        listOf(CustomLightPink, CustomDarkPink),
        listOf(CustomLightPurple, CustomDarkPurple),
        listOf(CustomLightSky, CustomDarkSky),
        listOf(CustomLightGreen, CustomDarkGreen),
        listOf(CustomLightOrange, CustomDarkOrange),
        listOf(CustomLightYellow, CustomDarkYellow),
        listOf(CustomLightParrot, CustomDarkParrot),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    TransparentStatusBar()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                CustomDrawerScreen(navHostController,check = searchState.notificationEnabled, onCheck = {
                    homeViewModel.onEvent(HomeUIEvent.EnabledNotification(it))
                })
            }
             },
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = DrawerDefaults.scrimColor
    ) {

        Scaffold(

            topBar = {
                QuoteTopBar(
                    title = "Home",
                    colorList = listOf(CustomLightPink, CustomDarkPink),
                    bookmarkAction = {
                        navHostController.navigate(Destination.BookMarkFirst)
                    },
                    navigationIcon = R.drawable.menu,
                    navigationAction = {
                        scope.launch{
                            drawerState.apply {
                                if(isClosed) open()
                                else
                                    close()
                            }
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxSize()

        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .padding(it),
                verticalArrangement = Arrangement.Center
            ) {

                item {

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        SearchBarQuote(
                            onValue = {
                                homeViewModel.onEvent(HomeUIEvent.UpdateSearchQuery(it)) },
                            text = searchState.searchQuery,
                            readOnly = false,
                            onSearch = {
                                homeViewModel.onEvent(HomeUIEvent.SearchQuote)
                            },
                            onTextFilled = {
                                homeViewModel.onEvent(HomeUIEvent.UpdateSearchQuery(""))
                            } )


                    }

                }


                if(searchState.quotes != null && searchState.searchQuery  != "") {
                    item {
                        Box(

                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSystemInDarkTheme()) CustomBlack else CustomLightWhite)
                                .fillMaxWidth()
                                .height(190.dp)
                        ){
                            LazyColumn(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(searchResults) { quote ->
                                    CustomSearchQuoteItem(
                                        quotations = quote,
                                        onShareClick = {

                                            val sendIntent = Intent().apply {
                                                action=Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_TEXT, "Quote: ${quote.quote}\nAuthor: ${quote.author ?: "Unknown"}")
                                                type = "text/plain"
                                            }
                                            val shareIntent = Intent.createChooser(
                                                sendIntent,"Quote"
                                            )
                                            activityContext.startActivity(shareIntent, )
                                        },
                                        color = CustomDarkPink
                                    )
                                }
                            }

                            if(searchResults.isEmpty()){
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier.clickable{

                                            homeViewModel.onEvent(HomeUIEvent.UpdateSearchQuery(""))
                                        },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "No Quote Found ", style = MaterialTheme.typography.labelLarge, color = CustomDarkOrange)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Icon(imageVector = Icons.Default.ErrorOutline, contentDescription = "", tint = CustomDarkPink, modifier = Modifier.size(50.dp))

                                    }
                                }
                            }
                        }
                    }
                }


                item {
                    Row(
                        modifier = Modifier.padding(start = 10.dp, end = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Saved Categories", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier
                                .widthIn(90.dp)
                                .clickable {
                                    viewAllClick.invoke()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "View All",
                                style = MaterialTheme.typography.labelLarge,
                                color = if(isSystemInDarkTheme() ) CustomLightWhite else  CustomBlack
                            )
                            Spacer(modifier = Modifier.heightIn(min =10.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.arrow),
                                contentDescription = "Arrow",
                                Modifier
                                    .rotate(180f),
                                tint = if(isSystemInDarkTheme() ) CustomLightWhite else  CustomBlack
                            )
                        }

                    }
                }


                //second navigate

                item {
                    Spacer(modifier = Modifier.heightIn(min =10.dp))
                }
                item {
                    LazyRow(
                        modifier = Modifier.padding(end = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(categories.distinct()) {
                            SavedCategories(colors = colors, text = it, navigateToQuotation = {
                                navHostController.navigate(Destination.QuotationSecond(category = it))
                            })
                        }

                    }

                }

                item {
                    Spacer(modifier = Modifier.heightIn(min = 15.dp))


                }

                item {
                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Text(text = "Quote of The Day", style = MaterialTheme.typography.titleLarge)

                    }
                }

                item {
                    Spacer(modifier = Modifier.heightIn(min = 5.dp))

                }

                item() {

                    var quote = if( todayQuote != LocalQuote("","",""))
                        todayQuote
                    else if(searchResults.isNotEmpty()) searchResults.random() else null


                    if(quote != null){
                        quote
                    }else {
                        LocalQuote(
                            category = "Waiting ",
                            author = "Wait a Little Bit",
                            quote = "Wait a Little Bit"
                        )
                    }?.let { item ->
                        CustomHomeQuoteItem(
                            quote =  item,
                            onBookmarkClick = {
                                Toast.makeText(activityContext,"You Can't Save this Quote ",Toast.LENGTH_SHORT).show()
                            },
                            onShareClick = {

                                val sendIntent = Intent(
                                    Intent.ACTION_SEND
                                ).apply {
                                    putExtra(Intent.EXTRA_TEXT, "Quote : ${item.quote}\n Author : ${item.author}")
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(
                                    sendIntent,"Quote"
                                )
                                activityContext.startActivity(shareIntent)

                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.heightIn(min = 5.dp))
                }




                item {

                    Row(
                        modifier = Modifier.padding(start = 10.dp, end = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Saved Quotes", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier
                                .widthIn(90.dp)
                                .clickable {
                                    navHostController.navigate(Destination.BookMarkFirst)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "View All",
                                style = MaterialTheme.typography.labelLarge,
                                color = if(isSystemInDarkTheme() ) CustomLightWhite else  CustomBlack
                            )
                            Spacer(modifier = Modifier.heightIn(min =10.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.arrow),
                                contentDescription = "Arrow",
                                Modifier
                                    .rotate(180f),
                                tint = if(isSystemInDarkTheme() ) CustomLightWhite else  CustomBlack
                            )
                        }

                    }
                }



                if(state.quotes.isNotEmpty() && state.quotes != null){
                    item {
                        val quotes = state.bookmarkedQuotes.isNotEmpty()
                        val quote = state.quotes.get(if(quotes) 1 else 0)

                        CustomSavedQuoteItem(
                            quotations = quote,
                            isBookMarked = false,
                            onBookmarkSave = {
                                Toast.makeText(activityContext,"You can't Delete Quote", Toast.LENGTH_SHORT).show()
                            },
                            onShareClick = {

                                val sendIntent = Intent().apply {
                                    action=Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Quote: ${quote.quote}\nAuthor: ${quote.author ?: "Unknown"}")
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(
                                    sendIntent,"Quote"
                                )
                                activityContext.startActivity(shareIntent, )
                            },
                            color = CustomDarkPink
                        )

                    }
                }


                item {
                    Spacer(modifier = Modifier.heightIn(min = 5.dp))

                }


            }



        }
    }



}

@Preview(showSystemUi = true)
@Composable
fun PreViewHomeScreen() {

//    HomeScreen(navController)
}