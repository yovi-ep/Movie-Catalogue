package com.yeputra.moviecatalogue.job

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.repository.api.ApiMovie
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.NotifUtils
import com.yeputra.moviecatalogue.utils.RestClient
import com.yeputra.moviecatalogue.utils.RxUtils
import com.yeputra.moviecatalogue.view.MainActivity
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

class JobReleaseRemainder : BroadcastReceiver() {
    private val TAG = JobReleaseRemainder::class.java.simpleName
    private var subsriber: Disposable? = null
    private val ID_RELEASE_REMAINDER = 102

    fun startReleaseRemainder(context: Context) {
        Log.d(TAG, "startReleaseRemainder")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, JobReleaseRemainder::class.java)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Constans.RELEASE_REMAINDER_TIME)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMAINDER, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    fun stopReleaseRemainder(context: Context) {
        Log.d(TAG, "stopReleaseRemainder")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, JobReleaseRemainder::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMAINDER, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->
            val date = Calendar.getInstance().time
            val sDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

            subsriber = RestClient()
                    .get()
                    .create(ApiMovie::class.java)
                    .getRelease(sDate, sDate, getLocale())
                    .compose(RxUtils.applyObservableAsync())
                    .subscribe {
                        it.results?.forEach { movie ->
                            sendNotification(ctx, movie)
                        }
                    }
        }
    }

    private fun sendNotification(context: Context, movie: Movie) {
        movie.original_title?.let {
            val intent = Intent(context, MainActivity::class.java)
            val msg = String.format(context.getString(R.string.release_remainder_msg, movie.original_title))

            NotifUtils.showStackNotification(
                    context,
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