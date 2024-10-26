package com.example.finalquote.presentation.category

import com.example.finalquote.R
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.finalquote.presentation.components.CategoryItem
import com.example.finalquote.presentation.components.QuoteTopBar
import com.example.finalquote.presentation.navigation.Destination
import com.example.finalquote.ui.theme.CustomDarkGreen
import com.example.finalquote.ui.theme.CustomDarkOrange
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomDarkPurple
import com.example.finalquote.ui.theme.CustomDarkSky
import com.example.finalquote.ui.theme.CustomDarkYellow
import com.example.finalquote.ui.theme.CustomLightGreen
import com.example.finalquote.ui.theme.CustomLightOrange
import com.example.finalquote.ui.theme.CustomLightPink
import com.example.finalquote.ui.theme.CustomLightPurple
import com.example.finalquote.ui.theme.CustomLightSky
import com.example.finalquote.ui.theme.CustomLightYellow

@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel,
) {


    val categories by categoryViewModel.categories.collectAsState()


    val colors = listOf(
        listOf(CustomLightPink, CustomDarkPink),
        listOf(CustomLightPurple, CustomDarkPurple),
        listOf(CustomLightSky, CustomDarkSky),
        listOf(CustomLightGreen, CustomDarkGreen),
        listOf(CustomLightOrange, CustomDarkOrange),
        listOf(CustomLightYellow, CustomDarkYellow),
    )


    val lazyGridState = rememberLazyGridState()

    Scaffold(

        topBar = {
            QuoteTopBar(
                title = "Category",
                colorList = listOf(CustomLightPink, CustomDarkPink),
                navigationIcon = R.drawable.arrow,
                bookmarkAction = {
                    navController.navigate(Destination.BookMarkFirst)

                }) {
                navController.navigateUp()
            }
        }

    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.weight(1f))
            LazyVerticalGrid(
                userScrollEnabled = true,
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.SpaceAround,
                state = lazyGridState,
                columns = GridCells.Fixed(2)
            ) {

                items(categories.distinct()) {

                    CategoryItem(colors = colors, text = it, onClick = {
                        navController.navigate(Destination.QuotationFirst(it))
                    })
                }


            }


        }

    }

}


@Composable
fun PreviewCategoryScreen() {
//    CategoryScreen()
}