package com.yeputra.moviecatalogue.services

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.NotifUtils
import java.util.*

class JobReleaseRemainder : JobService() {
    private val NOTIF_ID = 6355080

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
                if (cal.get(Calendar.HOUR_OF_DAY) == Constans.RELEASE_REMAINDER_TIME) {
                    NotifUtils.showNotification(
                            applicationContext,
                            applicationContext.getString(R.string.app_name),
                            "Ada yang baru nih!",
                            NOTIF_ID,
                            null
                    )
                }
            }

            jobFinished(it, false)
        }
    }


}