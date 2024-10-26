package com.example.finalquote.presentation.components

import com.example.finalquote.R
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
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
import com.example.finalquote.ui.theme.CustomLightWhite
import com.example.finalquote.ui.theme.CustomLightYellow
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteTopBar(
    title: String,
    colorList: List<Color>,
    navigationIcon: Int,
    bookmarkAction: () -> Unit,
    navigationAction: () -> Unit
) {

    TopAppBar(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colorList
                )
            )
            .padding(start = 10.dp, end = 10.dp),
        title = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title , style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,  color = CustomLightWhite )
            }

        } ,

        navigationIcon = {
            Icon(painter = painterResource(id = navigationIcon), contentDescription ="Menu" , modifier = Modifier
                .clickable { navigationAction() }
                .size(25.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = CustomLightWhite,
            navigationIconContentColor = Color.White, actionIconContentColor = Color.White,
            containerColor = Color.Transparent,
        ),
        actions = {

            if(title != "Bookmark")
                Icon(imageVector = Icons.Filled.Bookmark, contentDescription = "BookMark", modifier = Modifier
                    .clickable { bookmarkAction() }
                    .size(25.dp))
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true)
@Composable
fun PreviewQuoteTopBar() {

    // Use SystemUiController to control system bar colors
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true // Use dark icons if the status bar is light, else false for light icons

    // Set the status bar color to transparent
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent, // Make status bar transparent
            darkIcons = useDarkIcons // Whether to use dark icons or light
        )
    }

    val colors = listOf(
        listOf(CustomLightPink, CustomDarkPink),
        listOf(CustomLightPurple, CustomDarkPurple),
        listOf(CustomLightSky, CustomDarkSky),
        listOf(CustomLightGreen, CustomDarkGreen),
        listOf(CustomLightOrange, CustomDarkOrange),
        listOf(CustomLightYellow, CustomDarkYellow),
    )
    val text = listOf(
        "Design",
        "Motivation",
        "Art",
        "Beauty",
        "Failure",
        "Poetry"
    )
    TransparentStatusBar()
    Scaffold (

        topBar = {
            QuoteTopBar(
                title = "Design",
                colorList = listOf(CustomLightPink, CustomDarkPink),
                navigationIcon = R.drawable.arrow,
                bookmarkAction = { /*TODO*/ }) {

            }
        },
        modifier = Modifier.fillMaxSize()

    ){
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(horizontalArrangement = Arrangement.Center , modifier = Modifier.fillMaxWidth()) {

//                SearchBarQuote(search)
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center
            ) {


                items(10){
//                    CategoryItem(colors = colors, text =text )
                }
            }

        }






    }

}

@Composable
fun TransparentStatusBar() {
    val view = LocalView.current
    val window = (LocalContext.current as Activity).window

    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false) // Disable system window fit
        window.statusBarColor = android.graphics.Color.TRANSPARENT // Set transparent color
    }
}