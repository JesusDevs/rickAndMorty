package com.rickandmorty

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("TAG", "From: " + remoteMessage.from)
        Log.d("TAG", "Notification Message Body: " + remoteMessage.notification?.body)

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, remoteMessage.notification?.body, Toast.LENGTH_LONG).show()
        }
    }

}
