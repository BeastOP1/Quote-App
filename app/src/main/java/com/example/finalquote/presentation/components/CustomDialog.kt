package com.example.finalquote.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomLightPink
import com.example.finalquote.ui.theme.CustomWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(  onConfirmed : () -> Unit , onCancel: () -> Unit) {


    AlertDialog(onDismissRequest = {
        onCancel.invoke()
    },

        properties = DialogProperties(dismissOnBackPress = true , usePlatformDefaultWidth = true)
    ) {

        Card(
            modifier = Modifier.padding(10.dp)
                .fillMaxWidth()
                .height(180.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )

        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                        .fillMaxWidth()
                )

                Text("Are you Sure?", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Do you Really want to delete this quote?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                ) {

                    Button(
                        modifier = Modifier.shadow(
                            elevation = 20.dp,
                            spotColor = CustomDarkPink,
                            ambientColor = CustomLightPink
                        ).background(
                            brush = Brush.linearGradient(
                                listOf(
                                    CustomLightPink, CustomDarkPink
                                )
                            ),
                            shape = RoundedCornerShape(10.dp), alpha = 1f
                        ),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = CustomWhite,
                            containerColor = Color.Transparent
                        ),
                        onClick = {
                            onCancel.invoke()
                        }, shape = RoundedCornerShape(1.dp)
                    ) {
                        Text("No")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        modifier = Modifier.shadow(
                            elevation = 20.dp,
                            spotColor = CustomDarkPink,
                            ambientColor = CustomLightPink
                        ).background(
                            brush = Brush.linearGradient(
                                listOf(
                                    CustomLightPink, CustomDarkPink
                                ),

                                ),
                            shape = RoundedCornerShape(10.dp)
                        ),
                        shape = RoundedCornerShape(1.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = CustomWhite,
                            containerColor = Color.Transparent
                        ),
                        onClick = {
                            onConfirmed.invoke()
                        }
                    ) {
                        Text("Yes")
                    }

                }

                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                        .fillMaxWidth()
                )
            }
        }


    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreViewCustomAlertDialog() {

    CustomAlertDialog(onConfirmed = {}){}
}