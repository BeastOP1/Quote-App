package com.example.finalquote.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
fun SavedCategories(colors: List<List<Color>>, text: String, navigateToQuotation:(category:String) -> Unit) {

    val firstColor = remember{
        colors.random().first()}

    val secondColor = remember {

        colors.random().last()

    }




    Card(
        onClick = {
            navigateToQuotation(text)
        },
        modifier = Modifier
            .padding(10.dp, end = 15.dp)
            .shadow(
                elevation = 20.dp,
                ambientColor = firstColor,
                spotColor = secondColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(10.dp)),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )

    ) {
        Box(modifier = Modifier
            .width(140.dp)
            .height(125.dp)
            .background(
                brush = Brush.linearGradient(
                    colors.random()
                )
            )
            ,
            contentAlignment = Alignment.Center,

            ){

            Text(text = text , style = MaterialTheme.typography.titleMedium, color = Color.White , fontWeight = FontWeight.W400, maxLines = 1)
        }


    }

}


@Preview(showSystemUi = true)
@Composable
fun PreviewSavedCategories() {


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
    LazyRow(
        modifier = Modifier.height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        items(10){
//            SavedCategories(colors, text)

        }
    }
}