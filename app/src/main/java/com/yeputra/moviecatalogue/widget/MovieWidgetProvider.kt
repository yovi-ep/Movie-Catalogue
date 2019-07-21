package com.yeputra.moviecatalogue.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.job.JobFactory


/**
 * Implementation of App Widget functionality.
 */
class MovieWidgetProvider : AppWidgetProvider() {
    companion object {
        private const val TOAST_ACTION = "com.yeputra.moviecatalogue.TOAST_ACTION"
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

        context?.let {
            JobFactory(context).startUpdateWidgetContent()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)

        context?.let {
            JobFactory(context).stopUpdateWidgetContent()
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val intent = Intent(context, StackWidgetService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

        val views = RemoteViews(context.packageName, R.layout.movie_widget)
        views.setRemoteAdapter(R.id.stack_view, intent)
        views.setEmptyView(R.id.stack_view, R.id.empty_view)

        val toastIntent = Intent(context, MovieWidgetProvider::class.java)
        toastIntent.action = TOAST_ACTION
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

        val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

