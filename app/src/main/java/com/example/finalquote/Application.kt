package com.example.finalquote

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.finalquote.ui.theme.util.channelName
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
@HiltAndroidApp
class Application: Application(),Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    lateinit var workMangerConfiguration: Configuration


    override fun onCreate() {
        super.onCreate()


        createNotificationChannel(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
      return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Daily Quotes"
            val descriptionText = "Channel for daily quote notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelName, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
