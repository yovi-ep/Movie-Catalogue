package com.yeputra.moviecatalogue.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.yeputra.moviecatalogue.R


/**
 * Implementation of App Widget functionality.
 */
class MovieWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "com.yeputra.moviecatalogue.TOAST_ACTION"
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

