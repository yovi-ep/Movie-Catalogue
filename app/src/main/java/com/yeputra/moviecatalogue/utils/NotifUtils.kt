package com.yeputra.moviecatalogue.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


object NotifUtils {
    class NotifItem(val id: Int, val title: String, val message: String)
    private val GROUP_KEY_EMAILS = "notifGroup"
    private val MAX_NOTIF = 5
    private val stackNotif = mutableListOf<NotifItem>()
    private var idNotif = 0

    fun showNotification(context: Context, title: String, message: String, notifId: Int, pendingIntent: Intent?) {
        val channelId = context.packageName
        val channelName = context.packageCodePath

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = getNotificationBuilder(
                context, channelId, title, message, alarmSound, pendingIntent
        )
        setChannel(builder, channelId, channelName, notificationManagerCompat, alarmSound, message)
        notificationManagerCompat.notify(notifId, builder.build())
    }

    fun showStackNotification(context: Context, title: String, message: String, notifId: Int, pendingIntent: Intent?) {
        val channelId = context.packageName
        val channelName = context.packageCodePath

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = getNotificationBuilder(
                context, channelId, title, message, alarmSound, pendingIntent
        )

        stackNotif.add(NotifItem(idNotif, title, message))

        builder.setGroup(GROUP_KEY_EMAILS)
        if (idNotif > MAX_NOTIF) {
            val inboxStyle = NotificationCompat.InboxStyle()
                    .addLine(stackNotif[idNotif-2].message)
                    .addLine(stackNotif[idNotif-1].message)
                    .addLine(stackNotif[idNotif].message)
                    .setBigContentTitle(title)
            builder.setGroupSummary(true)
                    .setStyle(inboxStyle)
        }
        idNotif++
        setChannel(builder, channelId, channelName, notificationManagerCompat, alarmSound, message)
        notificationManagerCompat.notify(notifId, builder.build())
    }

    fun resetStackNotification() {
        idNotif = 0
        stackNotif.clear()
    }

    private fun getNotificationBuilder(
            context: Context,
            channelId: String,
            title: String,
            message: String,
            alarmSound: Uri?,
            pendingIntent: Intent?
    ) : NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
                    .setContentTitle(title)
                    .setSmallIcon(com.yeputra.moviecatalogue.R.mipmap.ic_launcher)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.black))
                    .setSound(alarmSound)
                    .setAutoCancel(true)
                    .setContentIntent(getPendingIntent(context, pendingIntent))

    private fun setChannel(
            builder: NotificationCompat.Builder,
            channelId: String,
            channelName: String,
            notificationManager: NotificationManager,
            alarmSound: Uri?,
            message: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channelId)
            notificationManager.createNotificationChannel(channel)

            channel.lightColor = Color.GRAY
            channel.enableLights(true)
            channel.description = message
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            channel.setSound(alarmSound, audioAttributes)
        }
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