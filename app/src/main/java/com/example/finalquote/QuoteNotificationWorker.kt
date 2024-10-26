package com.example.finalquote

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.finalquote.ui.theme.util.channelName
import com.example.finalquote.domain.model.LocalQuote
import com.example.finalquote.domain.usecases.QuoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
//Aditional Way for Getting Daily Quote
@RequiresApi(Build.VERSION_CODES.O)
@HiltWorker
class QuoteNotificationWorker  @AssistedInject constructor(

    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val quoteUseCase: QuoteUseCase,
    private val notificationManager: NotificationManagerCompat
) : CoroutineWorker(
    context, params) {

    override suspend fun doWork(): Result {
        Log.d("Quote","Im doing work")
        if (ContextCompat.checkSelfPermission(
                applicationContext, android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED) {

            // Fetch the quote and send the notification
            val quotes = quoteUseCase.getAllLocalQuote()
            sendNotification(quotes)

        } else {
            Log.d("QuoteNotificationWorker", "Permission not granted for notifications")
            // You can log this or try to handle it, e.g., request permission through UI, etc.
        }
        return Result.success()
    }

    private suspend fun sendNotification(quotes: Flow<List<LocalQuote>>) {
        // Assume you have logic here to send notifications with NotificationManagerCompat
        // For example, get the first quote and author, and send it
        quotes.collect { quoteList ->
            if (quoteList.isNotEmpty()) {
                val quote: LocalQuote = quoteList.random()  // Pick a random quote
                val notification = NotificationCompat.Builder(applicationContext, channelName)
                    .setContentTitle("Quote of The Day")
//                    .setStyle(
//                        NotificationCompat.BigTextStyle().bigText(quote.author)
//                    )
                    .setContentText("Quote: ${quote.quote} \n Author :  \"${ quote.author }\"  ")
                    .setSmallIcon(R.drawable.arrow)
                    .build()

                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
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
                notificationManager.notify(1, notification)  // Send notification
            }
        }
    }
}
