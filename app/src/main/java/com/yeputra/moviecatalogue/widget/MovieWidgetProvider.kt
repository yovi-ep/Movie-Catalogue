package com.yeputra.moviecatalogue.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews


/**
 * Implementation of App Widget functionality.
 */
class MovieWidgetProvider : AppWidgetProvider() {
    companion object {
        private const val ONCLICK = "com.yeputra.moviecatalogue.ONCLICK"
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        /* Service Widget */
        val intent = Intent(context, StackWidgetService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

        /* Remote View */
        val views = RemoteViews(context.packageName, com.yeputra.moviecatalogue.R.layout.movie_widget)
        views.setRemoteAdapter(com.yeputra.moviecatalogue.R.id.stack_view, intent)
        views.setEmptyView(com.yeputra.moviecatalogue.R.id.stack_view, com.yeputra.moviecatalogue.R.id.empty_view)

        /* Widget Provider */
        val clickIntent = Intent(context, MovieWidgetProvider::class.java)
        clickIntent.action = ONCLICK
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

        val toastPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setPendingIntentTemplate(com.yeputra.moviecatalogue.R.id.stack_view, toastPendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

