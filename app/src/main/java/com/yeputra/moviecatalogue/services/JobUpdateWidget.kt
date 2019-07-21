package com.yeputra.moviecatalogue.services

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.repository.storage.FavoriteService
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.widget.MovieWidgetProvider


class JobUpdateWidget : JobService() {
    private lateinit var favorite: FavoriteService

    override fun onStopJob(job: JobParameters): Boolean = true

    override fun onStartJob(job: JobParameters): Boolean {
        favorite = FavoriteService(applicationContext)
        loadData()
        jobFinished(job, false)
        return true
    }

    fun loadData() {
        favorite.findAll(FilmType.MOVIE) {
            it.forEach { movie ->
                movie.poster_path?.let { it1 -> loadImage(it1) }
            }
        }

        favorite.findAll(FilmType.TVSHOW) {
            it.forEach { movie ->
                movie.poster_path?.let {it1 -> loadImage(it1)}
            }
        }
    }

    private fun loadImage(path: String?) {
        Glide.with(applicationContext)
                .asBitmap()
                .load(Constans.POSTER_URL + Constans.POSTER_MEDIUM + path)
                .placeholder(R.mipmap.ic_placeholder)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        updateWidgetView(resource)
                    }
                })
    }

    private fun updateWidgetView(bitmap: Bitmap) {
        val manager = AppWidgetManager.getInstance(this)
        val view = RemoteViews(packageName, R.layout.widget_item)
        val theWidget = ComponentName(this, MovieWidgetProvider::class.java)
        view.setImageViewBitmap(R.id.imageView, bitmap)

        manager.updateAppWidget(theWidget, view)
    }
}