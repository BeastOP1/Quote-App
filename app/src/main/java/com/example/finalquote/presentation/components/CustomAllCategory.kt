package com.example.finalquote.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(colors: List<List<Color>>, text: String, onClick: (category: String) -> Unit) {



    Card(
        onClick = {
            onClick(text)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp)).padding(10.dp)
        ,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),

        ) {
        Box(modifier = Modifier
            .height(135.dp)
            .fillMaxWidth(1f)
            .background(
                brush = Brush.linearGradient(
                    colors
                        .random()
                        .reversed()
                )
            )

            ,
            contentAlignment = Alignment.Center
        ){
            Text(text = text , style = MaterialTheme.typography.titleLarge , color = Color.White ,  fontFamily = FontFamily.Default )
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewCategoryItem() {
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

    LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.SpaceBetween) {
        items(11){
            CategoryItem(colors, text.get(1), onClick = {})
        }
    }


}