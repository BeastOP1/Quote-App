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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomDarkPurple
import com.example.finalquote.ui.theme.CustomLightPink
import com.example.finalquote.ui.theme.CustomLightPurple
import com.example.finalquote.ui.theme.CustomLightWhite
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomHomeQuoteItem(quote: LocalQuote, onShareClick :() -> Unit,onBookmarkClick : () -> Unit) {


    val timestamp = System.currentTimeMillis()
    val sdf = SimpleDateFormat("dd MMMM yyyy ", Locale.getDefault())
    val day = SimpleDateFormat("EEEE", Locale.getDefault())
    val formattedDate = sdf.format(Date(timestamp))
    val Day =day.format(Date(timestamp))
    var i = 0

//    val q = if(quote.isNotEmpty()) quote[i] else null
//    val a = if(author.isNotEmpty()) author[i] else null
    Card(
        onClick = { /*TODO*/ },

        modifier = Modifier
            .padding(10.dp)
            .shadow(
                shape = RoundedCornerShape(0.dp),
                elevation = 45.dp,
                ambientColor = CustomLightPink,
                spotColor = CustomDarkPink
            )
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            CustomLightPink, CustomDarkPink
                        )
                    )
                )
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .height(175.dp)
                .padding(start = 5.dp, end = 5.dp)

        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(
                    modifier = Modifier
                        .heightIn(min = 10.dp)
                        .weight(1f)
                )

                Text(
                    text =  quote.quote,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    color = Color.White

                )

                Spacer(
                    modifier = Modifier
                        .heightIn(min = 10.dp)
                        .weight(1f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .width(30.dp)
                            .height(2.dp)
                            .background(CustomLightWhite)
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text =  quote.author.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = CustomLightWhite
                    )
                }
                Spacer(
                    modifier = Modifier
                        .heightIn(min = 10.dp)
                        .weight(1f)
                )

                Row(
                    modifier = Modifier.padding(start = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = Day,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = CustomLightWhite,
                            fontWeight = FontWeight.W200
                        )
                        Text(
                            text = formattedDate,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium,
                            color = CustomLightWhite,
                            fontWeight = FontWeight.W200
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.share_icon),
                            contentDescription = "Share",
                            modifier = Modifier.size(26.dp).clickable { onShareClick.invoke() },
                            tint = Color.White
                        )
                        Icon(
                            imageVector = Icons.Filled.BookmarkBorder,
                            contentDescription = "Share",
                            modifier = Modifier.clickable{onBookmarkClick.invoke()}
                                .padding(end = 5.dp)
                                .size(30.dp),
                            tint = CustomLightWhite
                        )

                    }


                }
                Spacer(modifier = Modifier.heightIn(min = 5.dp))

            }

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewCustomDayQuote(modifier: Modifier = Modifier) {

//    CustomHomeQuoteItem(today_quotes.value)
}