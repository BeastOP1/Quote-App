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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalquote.domain.model.Quote
import com.example.finalquote.ui.theme.CustomBlack
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomDarkPurple
import com.example.finalquote.ui.theme.CustomLightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteItem(quotations: Quote, onBookmarkSave: () -> Unit, isBookMarked: Boolean , onShareCLick :() -> Unit , saves : String) {

val saves = listOf(
    "67.2k","5.4k","7M","652","90.2k","3.9k","100k"
)
    Card(
        onClick = {},
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .padding(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(10.dp, pressedElevation = 90.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .padding(start = 15.dp, end = 5.dp)
                .background(Color.Transparent)
        ) {
            Column {


                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = quotations.quote!!,
                    style = MaterialTheme.typography.headlineMedium, color = CustomBlack
                )
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Spacer(
                        modifier = Modifier
                            .width(35.dp)
                            .height(2.dp)
                            .background(CustomDarkPink)
                    )
                    Text(
                        text = quotations.author!!,
                        style = MaterialTheme.typography.titleLarge,
                        color = CustomDarkPink
                    )

                }

                Row(
                    modifier = Modifier.padding(bottom = 15.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        if (isBookMarked) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { onBookmarkSave.invoke() },
                                imageVector = Icons.Filled.Bookmark,
                                contentDescription = "",
                                tint = CustomDarkPink
                            )
                        } else {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { onBookmarkSave.invoke() },
                                imageVector = Icons.Filled.BookmarkBorder,
                                contentDescription = "",
                                tint = CustomDarkPink
                            )
                        }
                        Text(
                            text = saves.distinct().random(),
                            style = MaterialTheme.typography.labelLarge,
                            color = CustomLightGray
                        )
                        Text(
                            text = "saves",
                            style = MaterialTheme.typography.labelLarge,
                            color = CustomLightGray
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.size(30.dp).clickable {
                            onShareCLick.invoke()
                        },
                        painter = painterResource(id = R.drawable.share_icon),
                        contentDescription = "Share",
                        tint = CustomDarkPink
                    )

                }
            }
        }

    }


}

@Preview(showSystemUi = true)
@Composable
fun PreviewQuoteItem() {

//    QuoteItem()


}