package com.example.finalquote.presentation.components

import com.example.finalquote.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.model.Quote
import com.example.finalquote.ui.theme.CustomBlack
import com.example.finalquote.ui.theme.CustomDarkOrange
import com.example.finalquote.ui.theme.CustomLightBlack
import com.example.finalquote.ui.theme.CustomLightPink
import com.example.finalquote.ui.theme.CustomLightPurple
import com.example.finalquote.ui.theme.CustomLightWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSavedQuoteItem(quotations: Quote , onBookmarkSave: ()-> Unit, isBookMarked: Boolean, onShareClick : () -> Unit , color : Color) {


    Card(onClick = {},
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp)
            .background(Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 60.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .padding(start = 15.dp, end = 5.dp).background(Color.Transparent)
        ) {

            Column {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = quotations.quote,
                    style = MaterialTheme.typography.bodyLarge, color = CustomBlack
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .width(35.dp)
                            .background(color)
                    )

                    Text(
                        text = quotations.author.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = CustomDarkOrange
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ), shape = RoundedCornerShape(5.dp), modifier = Modifier.clip(
                        RoundedCornerShape(5.dp)
                    ).background(brush = Brush.linearGradient(
                        listOf(CustomLightWhite,CustomLightPink, color)
                    )).height(35.dp)
                        .weight(1f)) {

                        Text(
                            text = quotations.category ,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }

                    if(!isBookMarked){
                        Icon(
                            modifier = Modifier.size(30.dp).clickable { onBookmarkSave()},
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = "",
                            tint = color
                        )
                    }
                    else {
                        Icon(
                            modifier = Modifier.size(30.dp).clickable{ onBookmarkSave.invoke()},
                            imageVector = Icons.Filled.BookmarkBorder,
                            contentDescription = "",
                            tint = CustomLightBlack
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.share_icon),
                        contentDescription = "Share",
                        modifier = Modifier.size(30.dp).clickable { onShareClick.invoke() },
                        tint = color
                    )
                }
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchQuoteItem(quotations: LocalQuote , onShareClick : () -> Unit , color : Color) {


    Card(onClick = {},
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp)
            .background(Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp, pressedElevation = 60.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .padding(start = 15.dp, end = 5.dp).background(Color.Transparent)
        ) {

            Column {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = quotations.quote,
                    style = MaterialTheme.typography.bodyLarge, color = CustomBlack
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .width(35.dp)
                            .background(color)
                    )

                    Text(
                        text = quotations.author.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = CustomDarkOrange
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ), shape = RoundedCornerShape(5.dp), modifier = Modifier.clip(
                        RoundedCornerShape(5.dp)
                    ).background(brush = Brush.linearGradient(
                        listOf(CustomLightWhite, color)
                    )).height(35.dp)
                        .weight(1f)) {

                        Text(
                            text = quotations.category ,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id =R.drawable.share_icon),
                        contentDescription = "Share",
                        modifier = Modifier.size(30.dp).clickable { onShareClick.invoke() },
                        tint = color
                    )
                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewCustomSavedQuoteItem() {

//    CustomSavedQuoteItem()
}