package com.example.finalquote.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.finalquote.presentation.bookmark.BookmarkScreen
import com.example.finalquote.presentation.bookmark.BookmarkViewModel
import com.example.finalquote.presentation.category.CategoryScreen
import com.example.finalquote.presentation.category.CategoryViewModel
import com.example.finalquote.presentation.home.HomeScreen
import com.example.finalquote.presentation.home.HomeViewModel
import com.example.finalquote.presentation.quote.QuotationScreen
import com.example.finalquote.presentation.quote.QuoteViewModel

@Composable
fun Quote_Navigator(
    applicationContext: Context,
    navController: NavHostController,
    activityContext: Context,
    categoryViewModel: CategoryViewModel,
    homeViewModel: HomeViewModel
) {


    NavHost(navController = navController, startDestination = Screen.Home) {


        navigation<Screen.Home>(startDestination = Destination.HomeFirst) {
            composable<Destination.HomeFirst> {
                val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
                val quoteViewModel: QuoteViewModel = hiltViewModel()
                HomeScreen(
                    navHostController = navController,
                    viewAllClick = {
                        navController.navigate(Destination.CategoryFirst)
                    }, activityContext = activityContext,
                    homeViewModel = homeViewModel,
                    categoryViewModel = categoryViewModel,
                    bookmarkViewModel = bookmarkViewModel,
                    quoteViewModel = quoteViewModel
                )
            }

            composable<Destination.HomeSecond> {
                val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
                val quoteViewModel: QuoteViewModel = hiltViewModel()

                HomeScreen(
                    navHostController = navController,
                    viewAllClick = {
                        TODO()
                    }, activityContext = activityContext,
                    homeViewModel = homeViewModel,
                    categoryViewModel = categoryViewModel,
                    bookmarkViewModel = bookmarkViewModel,
                    quoteViewModel = quoteViewModel
                )
            }


        }


        navigation<Screen.Category>(startDestination = Destination.CategoryFirst) {
            composable<Destination.CategoryFirst> {
                CategoryScreen(navController = navController, categoryViewModel = categoryViewModel)
            }
        }

        navigation<Screen.Quotation>(startDestination = Destination.QuotationFirst("")) {

            // Use an empty string if category is not provided
            composable<Destination.QuotationFirst> { backStackEntry ->

                val category = backStackEntry.arguments?.getString("category") ?: ""
                val quoteViewModel: QuoteViewModel = hiltViewModel()
                val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

                QuotationScreen(
                    navController = navController,
                    categoryName = category,
                    context = applicationContext,
                    activityContext = activityContext,
                    bookmarkViewModel = bookmarkViewModel,
                    quoteViewModel = quoteViewModel
                )
            }


            composable<Destination.QuotationSecond> { category ->
                val category: String = category.arguments?.getString("category") ?: ""
                val quoteViewModel: QuoteViewModel = hiltViewModel()
                val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

                QuotationScreen(
                    navController = navController,
                    categoryName = category,
                    context = applicationContext,
                    activityContext = activityContext,
                    bookmarkViewModel = bookmarkViewModel,
                    quoteViewModel = quoteViewModel
                )
            }
        }

        navigation<Screen.BookMark>(startDestination = Destination.BookMarkFirst) {

            composable<Destination.BookMarkFirst> {
                val quoteViewModel: QuoteViewModel = hiltViewModel()
                val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

                BookmarkScreen(
                    navController = navController,
                    context = applicationContext,
                    activityContext = activityContext,
                    quoteViewModel = quoteViewModel,
                    bookmarkViewModel = bookmarkViewModel
                )
            }

        }
    }

}
