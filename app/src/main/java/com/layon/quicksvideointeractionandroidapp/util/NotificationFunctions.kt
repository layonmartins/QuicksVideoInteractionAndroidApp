package com.layon.quicksvideointeractionandroidapp.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import com.layon.quicksvideointeractionandroidapp.R

lateinit var notificationManager: NotificationManager
lateinit var notificationChannel: NotificationChannel
lateinit var builder: Notification.Builder
private const val channelId = "i.apps.notifications"
private const val description = "Test notification"

fun createNotification(context : Context) : Notification {

    notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // checking if android version is greater than oreo(API 26) or not
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_LOW)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(context, channelId)
            .setContentTitle("WorkManagerNotification")
            .setContentText("WorkManager Info")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background))
            .setOngoing(true)
            .setAutoCancel(true)
    } else {
        builder = Notification.Builder(context)
            .setContentTitle("WorkManagerNotification")
            .setContentText("WorkManager Info")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background))
            .setOngoing(true)
            .setAutoCancel(true)
    }

    return builder.build()
}