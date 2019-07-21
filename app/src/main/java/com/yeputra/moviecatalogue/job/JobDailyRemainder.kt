package com.yeputra.moviecatalogue.job

import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.NotifUtils
import com.yeputra.moviecatalogue.view.MainActivity
import java.util.*

class JobDailyRemainder : JobService() {
    private val TAG = JobDailyRemainder::class.java.simpleName
    private val NOTIF_ID = 902389623

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
        if (SettingPref(applicationContext).dailyRemainder) {
            val cal = Calendar.getInstance()
            if (cal.get(Calendar.HOUR_OF_DAY) == Constans.DAILY_REMAINDER_TIME) {
                sendNotification()
            }
        }

        jobFinished(job, false)
    }

    private fun sendNotification() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        NotifUtils.showNotification(
                applicationContext,
                applicationContext.getString(R.string.app_name),
                applicationContext.getString(R.string.daily_remainder_msg),
                NOTIF_ID, intent)
    }
}