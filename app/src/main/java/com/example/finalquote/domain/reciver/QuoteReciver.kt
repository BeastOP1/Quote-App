package com.example.finalquote.domain.reciver

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.finalquote.MainActivity
import com.example.finalquote.R
import com.example.finalquote.ui.theme.util.channelName
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.usecases.QuoteUseCase
import com.example.finalquote.ui.theme.CustomLightPurple
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class QuoteReciver : BroadcastReceiver() {
private lateinit var notificationManager: NotificationManagerCompat
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Quote", "onReceive triggered")
        if(context != null){
            val entryPoint = EntryPointAccessors.fromApplication(
                context.applicationContext,
                QuoteReceiverEntryPoint::class.java
            )

            val quoteUseCase = entryPoint.quoteUseCase()
             notificationManager = entryPoint.notificationManager()
            CoroutineScope(Dispatchers.IO).launch{
                val q: Flow<List<LocalQuote>> = quoteUseCase.getAllLocalQuote()

                if(q != null){
                    sendNotification(
                        context,
                        q,
                        entryPoint.notificationManager())

                }

            }
        }
    }

}
 var todayQuote : LocalQuote = LocalQuote("","","")
private suspend fun sendNotification(
    context: Context,
    quote: Flow<List<LocalQuote>>,
    notificationManager: NotificationManagerCompat
){
    val notificationMa = notificationManager
    val notificationIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            putExtra("quote_text", quote.size) // Pass quote text
//            putExtra("quote_author", quote) // Pass quote author
    }

    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        notificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    quote.collect{ quoteList ->
        if(quoteList.isNotEmpty()){
            val quotes: LocalQuote = quoteList.random()
            todayQuote = LocalQuote(quote = quotes.quote, author = quotes.author, category = quotes.category)
            val notification = NotificationCompat.Builder(
                context, channelName
            ).setSmallIcon(R.drawable.quote12)
                .setColor(CustomLightPurple.value.toInt())
                .setColorized(true)
                .setContentTitle("Quote of a Day")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("\nQuote : \" ${quotes.quote} \"\n\n Author : \" ${quotes.author} \" ")
                )
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            notificationMa.notify(1,notification)
        }

    }


}
@EntryPoint
@InstallIn(SingletonComponent::class)
interface QuoteReceiverEntryPoint {
    fun quoteUseCase(): QuoteUseCase
    fun notificationManager(): NotificationManagerCompat

}