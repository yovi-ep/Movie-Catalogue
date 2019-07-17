package com.yeputra.moviecatalogue.services

import android.content.Intent
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.NotifUtils
import com.yeputra.moviecatalogue.view.MainActivity
import java.util.*

class JobDailyRemainder : JobService() {
    private val NOTIF_ID = 902389623

    override fun onStartJob(job: JobParameters?): Boolean {
        checkingRemainderTime(job)
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return true
    }

    private fun checkingRemainderTime(job: JobParameters?) {
        job?.let {
            if (SettingPref(applicationContext).dailyRemainder) {
                val cal = Calendar.getInstance()
                if (cal.get(Calendar.HOUR_OF_DAY) == Constans.DAILY_REMAINDER_TIME) {
                    sendNotification()
                }
            }

            jobFinished(it, false)
        }
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