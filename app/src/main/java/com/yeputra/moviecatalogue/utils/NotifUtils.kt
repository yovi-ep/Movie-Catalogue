package com.yeputra.moviecatalogue.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


object NotifUtils {

    fun showNotification(context: Context, title: String, message: String, notifId: Int, pendingIntent: Intent?) {
        val channelId = context.packageName
        val channelName = context.packageCodePath

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setSmallIcon(com.yeputra.moviecatalogue.R.mipmap.ic_launcher)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setSound(alarmSound)
                .setContentIntent(getPendingIntent(context, pendingIntent))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)

            channel.lightColor = Color.GRAY
            channel.enableLights(true)
            channel.description = message
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            channel.setSound(alarmSound, audioAttributes)
        }

        notificationManagerCompat.notify(notifId, builder.build())
    }

    private fun getPendingIntent(context: Context, pendingIntent: Intent?) : PendingIntent? =
            pendingIntent?.let {
                pendingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                return PendingIntent.getActivity(context,
                        System.currentTimeMillis().toInt(),
                        pendingIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT)
            } ?: run {
                null
            }
}