package com.example.thailotto2021_1.other

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()){
            Timber.d("onMessageReceive ${remoteMessage.data}")
        }
        remoteMessage.notification?.let {
            //Log.d(TAG, "Message Notification Body: ${it.body}")
            Timber.d("Message Notification Body: ${it.body}")
        }

    }
}