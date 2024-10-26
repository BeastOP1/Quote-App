package com.example.finalquote.presentation.components

import com.example.finalquote.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomDarkPurple
import com.example.finalquote.ui.theme.CustomLightBlack
import com.example.finalquote.ui.theme.CustomLightBlack2
import com.example.finalquote.ui.theme.CustomLightPink
import com.example.finalquote.ui.theme.CustomLightWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarQuote(
    text: String,
    onValue : (String) -> Unit,
    readOnly : Boolean,
    onClick :(() -> Unit)? = null,
    onSearch : () -> Unit,
    onTextFilled:() -> Unit
) {


    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isClicked  = interactionSource.collectIsPressedAsState().value

    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            onClick?.invoke()
        }
    }



    Card(onClick = { /*TODO*/ } ,
        modifier = Modifier.background(Color.Transparent)
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if(isSystemInDarkTheme()) CustomLightBlack else CustomLightWhite
        )
    ) {

        TextField(
            readOnly = readOnly,
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onValue
            ,
            label = {
                Text(text = "Search Quote", style = MaterialTheme.typography.labelLarge )
            },

            colors = TextFieldDefaults.colors(
                focusedLabelColor = CustomDarkPink,
                unfocusedLabelColor =  if(isSystemInDarkTheme() ) CustomLightWhite else   CustomLightBlack,
                focusedContainerColor = if(isSystemInDarkTheme()) CustomLightBlack2 else CustomLightWhite,
                unfocusedContainerColor =if(isSystemInDarkTheme()) CustomLightBlack2 else CustomLightWhite,
                unfocusedTextColor = CustomLightBlack,
                focusedTextColor = if(isSystemInDarkTheme()) CustomLightWhite else CustomLightBlack,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = CustomDarkPink
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = {
                if(text == ""){

                    Icon(painter = painterResource(id = R.drawable.icon), contentDescription ="search" ,
                        modifier = Modifier.size(30.dp),
                        tint = CustomDarkPink
                    )
                }
                else{

                    Icon(imageVector = Icons.Default.Close, contentDescription ="" ,
                        modifier = Modifier.clickable{
                            onTextFilled.invoke()
                        }.size(30.dp),
                        tint = CustomLightPink
                    )
                }

            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                },
                onDone = {
                    KeyboardActions.Default.onDone
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            interactionSource = interactionSource


        )
    }
}

//@Preview(uiMode = UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun PreViewSearchBarQuote(modifier: Modifier = Modifier) {

//    SearchBarQuote("search")


}