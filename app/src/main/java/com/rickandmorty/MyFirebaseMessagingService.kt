package com.rickandmorty

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rickandmorty.ui.searchCharacter.SearchCharacterFragment

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.data.isNotEmpty().let {
            remoteMessage.data.values.forEach {clave->
                Log.d("TAG", "Message data payload: $it")
                if (clave == "url") {
                    startActivity(Intent(this, SearchCharacterFragment::class.java))
                } else if (clave == "Morty") {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(this, "Morty", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, remoteMessage.notification?.body, Toast.LENGTH_LONG).show()
        }
    }

}
