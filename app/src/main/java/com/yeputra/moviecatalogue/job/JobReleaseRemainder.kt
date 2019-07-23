package com.yeputra.moviecatalogue.job

import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.job.JobFactory.Companion.ID_RELEASE_REMAINDER
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.repository.api.ApiMovie
import com.yeputra.moviecatalogue.repository.preference.SettingPref
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.NotifUtils
import com.yeputra.moviecatalogue.utils.RestClient
import com.yeputra.moviecatalogue.utils.RxUtils
import com.yeputra.moviecatalogue.view.MainActivity
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

class JobReleaseRemainder : JobService() {
    private val TAG = JobReleaseRemainder::class.java.simpleName
    private var subsriber: Disposable? = null

    override fun onStartJob(job: JobParameters): Boolean {
        Log.d(TAG, "started")
        checkingRemainderTime(job)
        return true
    }

    override fun onStopJob(job: JobParameters): Boolean {
        Log.d(TAG, "stopped")
        subsriber?.dispose()
        return true
    }

    private fun checkingRemainderTime(job: JobParameters) {
        val pref = SettingPref(applicationContext)
        val cal = Calendar.getInstance()

        if (pref.releaseRemainder) {
            if (cal.get(Calendar.HOUR_OF_DAY) == Constans.RELEASE_REMAINDER_TIME) {
                if (!pref.isRemaindRelease) {
                    pref.isRemaindRelease = true
                    getReleaseMovie(cal.time)
                }
            } else {
                pref.isRemaindRelease = false
            }
        }
        jobFinished(job, false)
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

            NotifUtils.showStackNotification(
                    applicationContext,
                    it, msg,
                    ID_RELEASE_REMAINDER, intent
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