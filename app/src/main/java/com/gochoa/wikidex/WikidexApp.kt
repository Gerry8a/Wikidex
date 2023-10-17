package com.gochoa.wikidex

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.gochoa.wikidex.utils.Dictionary.NOTIFICATION_CHANNEL_ID
import com.gochoa.wikidex.utils.Dictionary.NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WikidexApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}