package com.yeputra.moviecatalogue.services

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.yeputra.moviecatalogue.widget.MovieWidget


class StackRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = mutableListOf<Bitmap>()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = drawable?.let { DrawableCompat.wrap(it).mutate() }
        }

        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
    override fun onDataSetChanged() {
        mWidgetItems.add(getBitmapFromVectorDrawable(context, com.yeputra.moviecatalogue.R.drawable.ic_favorite))
        mWidgetItems.add(getBitmapFromVectorDrawable(context, com.yeputra.moviecatalogue.R.drawable.ic_movie))
        mWidgetItems.add(getBitmapFromVectorDrawable(context, com.yeputra.moviecatalogue.R.drawable.ic_favorite))
        mWidgetItems.add(getBitmapFromVectorDrawable(context, com.yeputra.moviecatalogue.R.drawable.ic_movie))
        mWidgetItems.add(getBitmapFromVectorDrawable(context, com.yeputra.moviecatalogue.R.drawable.ic_favorite))
        mWidgetItems.add(getBitmapFromVectorDrawable(context, com.yeputra.moviecatalogue.R.drawable.ic_movie))
    }

    override fun hasStableIds(): Boolean  = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, com.yeputra.moviecatalogue.R.layout.widget_item)
        rv.setImageViewBitmap(com.yeputra.moviecatalogue.R.id.imageView, mWidgetItems[position])

        val extras = Bundle()
        extras.putInt(MovieWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(com.yeputra.moviecatalogue.R.id.imageView, fillInIntent)
        return rv

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}