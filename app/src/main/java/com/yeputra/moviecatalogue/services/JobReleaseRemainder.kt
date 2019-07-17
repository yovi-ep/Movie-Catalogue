package com.yeputra.moviecatalogue.services

import android.content.Intent
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.repository.api.ApiMovie
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.utils.NotifUtils
import com.yeputra.moviecatalogue.utils.RestClient
import com.yeputra.moviecatalogue.utils.RxUtils
import com.yeputra.moviecatalogue.view.MainActivity
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

class JobReleaseRemainder : JobService() {
    private val NOTIF_ID = 6355080
    private var subsriber: Disposable? = null

    override fun onStartJob(job: JobParameters?): Boolean {
        checkingRemainderTime(job)
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        subsriber?.dispose()
        return true
    }

    private fun checkingRemainderTime(job: JobParameters?) {
        job?.let {
            if (SettingPref(applicationContext).dailyRemainder) {
                val cal = Calendar.getInstance()
                if (cal.get(Calendar.HOUR_OF_DAY) == 6){//Constans.RELEASE_REMAINDER_TIME) {
                    getReleaseMovie(cal.time)
                }
            }
            jobFinished(it, false)
        }
    }

    private fun getReleaseMovie(date: Date) {
        val sDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

        subsriber = RestClient()
                .get()
                .create(ApiMovie::class.java)
                .getRelease(sDate, sDate, getLocale())
                .compose(RxUtils.applyObservableAsync())
                .subscribe {
                    it.results?.forEach { movie ->
                        sendNotification(movie)
                    }
                }
    }

    private fun sendNotification(movie: Movie) {
        movie.original_title?.let {
            val intent = Intent(applicationContext, MainActivity::class.java)
            val msg = String.format(getString(R.string.release_remainder_msg, movie.original_title))

            NotifUtils.showNotification(
                    applicationContext,
                    it, msg,
                    NOTIF_ID, intent
            )
        }
    }

    private fun getLocale() : String {
        val lang = Locale.getDefault().language
        return if (lang == "in")
            "id"
        else lang
    }
}