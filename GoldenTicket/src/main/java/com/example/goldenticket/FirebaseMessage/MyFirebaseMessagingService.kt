package com.example.goldenticket.FirebaseMessage

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.goldenticket.Activity.MainActivity
import com.example.goldenticket.R
import com.example.goldenticket.R.drawable.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService  : FirebaseMessagingService(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(ContentValues.TAG, "From: " + remoteMessage!!.from!!)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(ContentValues.TAG, "Message data payload: " + remoteMessage.data)

            sendNotification(remoteMessage)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(ContentValues.TAG, "Message Notification Body: " + remoteMessage.notification!!.body!!)
        }

    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"]
        val content = remoteMessage.data["content"]

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "Golden Ticket"

        val intent = PendingIntent.getActivity(this, 0, Intent(applicationContext, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT)

        //채널 생성
        val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,"GoldenTicket Notification",
            NotificationManager.IMPORTANCE_MAX)

        notificationChannel.description="GoldenTicket Notification channel"
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = (Color.RED)
        notificationChannel.vibrationPattern = longArrayOf(0,1000,500,500)
        notificationChannel.enableVibration(true)

        //생성된 채널을 알림 설정에 추가
        notificationManager.createNotificationChannel(notificationChannel)

        //알림창 설정하기
        val notificationBuilder = NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(push_alarm_icon)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(intent)

        //설정한 알림창을 알림 설정에 추가
        notificationManager.notify(1,notificationBuilder.build())
    }
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String?) {
        Log.d(ContentValues.TAG, "Refreshed token: " + token!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d("TOKEN", "token: " + token!!)
    }

}