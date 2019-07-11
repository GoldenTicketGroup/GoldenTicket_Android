package com.example.goldenticket.FirebaseMessage

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService


class MyFirebaseInstanceIDService : FirebaseMessagingService(){
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)

        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("MyFirebase", "Refresh token: "+ refreshedToken!!)

        //Send this token to server for later use
    }
}