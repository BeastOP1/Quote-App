package com.example.finalquote

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.finalquote.domain.reciver.QuoteReciver
import com.example.finalquote.presentation.category.CategoryViewModel
import com.example.finalquote.presentation.home.HomeViewModel
import com.example.finalquote.presentation.navigation.Quote_Navigator
import com.example.finalquote.ui.theme.FinalQuoteTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()    {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //that's for transparent Top bar
        WindowCompat.setDecorFitsSystemWindows(window,false)
        window.statusBarColor = Color.TRANSPARENT
        installSplashScreen()
        setContent {
            FinalQuoteTheme {
                val navController = rememberNavController()
                val activityContext =  LocalContext.current
                val categoryViewModel: CategoryViewModel = hiltViewModel()
                val  homeViewModel: HomeViewModel = hiltViewModel()
                RequestNotificationPermission {
                    // Once permission is granted, proceed to set the alarm
                    if (homeViewModel.searchState.value.notificationEnabled) {
                        setDailyQuoteAlarm(this@MainActivity)
                    }
                }
                Quote_Navigator(
                    applicationContext, navController, activityContext,
                    categoryViewModel = categoryViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}


@Composable
fun RequestNotificationPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {

                onPermissionGranted()
            }
        }
    )

    LaunchedEffect(Unit) {
        // Check if the API level is 33 (Android 13) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if the permission is already granted
            if (ContextCompat.checkSelfPermission(
                    context,android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request permission if not granted
                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Permission is already granted, proceed
                onPermissionGranted()
            }
        } else {
            // If the device is running below Android 13, no need to request permission
            onPermissionGranted()
        }
    }
}

fun scheduleDailyQuoteNotification(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<QuoteNotificationWorker>(1, TimeUnit.MINUTES)
//        .setInitialDelay(1, TimeUnit.MINUTES) // You can modify this for testing or real delay
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "daily_quote_work",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )
}


fun setDailyQuoteAlarm(context: Context) {


    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, QuoteReciver::class.java) // Your BroadcastReceiver

    // Set the alarm to start at a specific time (e.g., 8:00 AM)
    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY , 5) // Set hour to 8 AM
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND ,0)
    }

    // Create a PendingIntent
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    if (calendar.timeInMillis <= System.currentTimeMillis()) {
        // If it’s already past 5:00 AM today, set for 5:00 AM the next day
        calendar.add(Calendar.HOUR, 5)
    }
    // Set a repeating alarm
    alarmManager.
    setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis ,
        AlarmManager.INTERVAL_DAY, // Repeat daily
        pendingIntent
    )
}