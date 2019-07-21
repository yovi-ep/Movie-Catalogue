package com.yeputra.moviecatalogue.job

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.presenter.WidgetPresenter
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_MEDIUM
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_URL
import com.yeputra.moviecatalogue.widget.MovieWidgetProvider
import java.net.URL


class JobUpdateWidget : JobService() {
    private val TAG = JobUpdateWidget::class.java.simpleName
    private lateinit var widgetPresenter: WidgetPresenter

    override fun onStopJob(job: JobParameters): Boolean {
        Log.d(TAG, "stopped")
        return true
    }

    override fun onStartJob(job: JobParameters): Boolean {
        Log.d(TAG, "started")
        widgetPresenter = WidgetPresenter(applicationContext)

        jobFinished(job, updateWidgetView())
        return true
    }

    private fun updateWidgetView() : Boolean {
        val datas = widgetPresenter.getWidgetContent()
        val manager = AppWidgetManager.getInstance(this)
        val rv = RemoteViews(packageName, R.layout.widget_item)
        val theWidget = ComponentName(this, MovieWidgetProvider::class.java)

        for (movie in datas) {
            try {
                val imageUrl = URL(POSTER_URL + POSTER_MEDIUM + movie.poster_path)
                val bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
                rv.setImageViewBitmap(R.id.imageView, bitmap)
                rv.setTextViewText(R.id.tv_title, movie.origTitle)
                manager.updateAppWidget(theWidget, rv)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
                return true
            }
        }
        return false
    }
}