package com.yeputra.moviecatalogue.job

import android.content.Context
import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.NotifUtils
import com.yeputra.moviecatalogue.view.MainActivity
import java.util.*

class JobDailyRemainder : JobService() {
    private val TAG = JobDailyRemainder::class.java.simpleName

    override fun onStartJob(job: JobParameters): Boolean {
        Log.d(TAG, "started")
        checkingRemainderTime(job)
        return true
    }

    override fun onStopJob(job: JobParameters): Boolean {
        Log.d(TAG, "stopped")
        return true
    }

    private fun checkingRemainderTime(job: JobParameters) {
        val pref = SettingPref(applicationContext)
        val cal = Calendar.getInstance()

        if (pref.dailyRemainder) {
            if (cal.get(Calendar.HOUR_OF_DAY) == Constans.DAILY_REMAINDER_TIME) {
                if (!pref.isRemaindDaily) {
                    sendNotification(applicationContext)
                    pref.isRemaindDaily = true
                }
            } else {
                pref.isRemaindDaily = false
            }
        }

        jobFinished(job, false)
    }

    private fun sendNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        NotifUtils.showNotification(
                context,
                context.getString(com.yeputra.moviecatalogue.R.string.app_name),
                context.getString(com.yeputra.moviecatalogue.R.string.daily_remainder_msg),
                JobFactory.ID_DAILY_REMAINDER, intent)
    }
}