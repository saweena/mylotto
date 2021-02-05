package com.example.thailotto2021_1.other

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.other.Constants.CHANNEL_ID
import com.example.thailotto2021_1.other.Constants.CHANNEL_NAME
import com.example.thailotto2021_1.other.Constants.NOTIFICATION_ID
import com.example.thailotto2021_1.ui.MainActivity
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()){
            Timber.d("onMessageReceive ${remoteMessage.data}")

        }

            remoteMessage.notification?.let {
                //Log.d(TAG, "Message Notification Body: ${it.body}")
                Timber.d("Message Notification Body: ${it.body}")
                createNotificationChannel()

                val intent = Intent(this, MainActivity::class.java)
                val pendingIntent = TaskStackBuilder.create(this).run {
                    addNextIntentWithParentStack(intent)
                    getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
                }
                val notification= NotificationCompat.Builder(this,CHANNEL_ID)
                        .setContentTitle(it.title ?: "")
                        .setContentText(it.body ?: "")
                        .setSmallIcon(R.drawable.ic_money)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .build()

                NotificationManagerCompat.from(this).notify(NOTIFICATION_ID,notification)
            }
        }
    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
    }
