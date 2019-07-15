package com.yeputra.moviecatalogue.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.yeputra.moviecatalogue.R

object NotifUtils {

    fun showNotification(context: Context, title: String, message: String, notifId: Int) {
        val channelId = context.packageName
        val channelName = context.packageCodePath

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        notificationManagerCompat.notify(notifId, builder.build())
    }

}