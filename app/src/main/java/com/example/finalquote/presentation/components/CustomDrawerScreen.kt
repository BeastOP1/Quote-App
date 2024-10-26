package com.example.finalquote.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.HorizontalDivider

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.finalquote.presentation.navigation.Destination
import com.example.finalquote.ui.theme.CustomDarkPink
import com.example.finalquote.ui.theme.CustomLightPink
import com.example.finalquote.ui.theme.CustomLightYellow
import com.example.finalquote.ui.theme.CustomWhite

@Composable
fun CustomDrawerScreen(
    navHostController: NavHostController,
    check: Boolean,
    onCheck: (Boolean) -> Unit
) {

    val selected2 = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .background(
                    brush = Brush.linearGradient(
                        listOf(CustomLightPink, CustomDarkPink)
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Quote",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }
        Spacer(modifier = Modifier.heightIn(min = 20.dp))
        NavigationDrawerItem(
            label = {
                Text(text = "Category")
            },
            selected = selected2.value,
            onClick = {
                navHostController.navigate(Destination.CategoryFirst)
                selected2.value = true
            },
            modifier = Modifier,
            icon = {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = CustomDarkPink
                )
            },
            badge = null,
            colors = NavigationDrawerItemDefaults.colors(
                selectedIconColor = CustomWhite,
                unselectedIconColor = Color.Transparent,
                selectedContainerColor = CustomWhite,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.heightIn(min = 10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "",
                tint = CustomDarkPink
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "Daily Notification",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = check,
                onCheckedChange = onCheck,
                modifier = Modifier,
                thumbContent = {
                    if (check) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = "Notification ON",
                        )

                    } else {
                        null
                    }
                },
                enabled = true,
                colors = SwitchDefaults.colors(
                    checkedIconColor = CustomDarkPink,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = CustomDarkPink,
                    checkedTrackColor = CustomDarkPink,
                    uncheckedTrackColor = Color.White,
                    uncheckedThumbColor = CustomDarkPink,
                    checkedThumbColor = Color.White
                )
            )

        }
    }
}