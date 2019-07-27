package com.yeputra.moviecatalogue.job

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.NotifUtils
import com.yeputra.moviecatalogue.view.MainActivity
import java.util.*

class JobDailyRemainder : BroadcastReceiver() {
    private val TAG = JobDailyRemainder::class.java.simpleName
    private val ID_DAILY_REMAINDER = 100

    fun startDailyRemainder(context: Context) {
        Log.d(TAG, "startDailyRemainder")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, JobDailyRemainder::class.java)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Constans.DAILY_REMAINDER_TIME)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMAINDER, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun stopDailyRemainder(context: Context) {
        Log.d(TAG, "stopDailyRemainder")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, JobDailyRemainder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMAINDER, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive")
        context?.let { sendNotification(it) }
    }

    private fun sendNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        NotifUtils.showNotification(
                context,
                context.getString(com.yeputra.moviecatalogue.R.string.app_name),
                context.getString(com.yeputra.moviecatalogue.R.string.daily_remainder_msg),
                ID_DAILY_REMAINDER, intent)
    }
}