package com.yeputra.moviecatalogue.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.MovieFavorite
import com.yeputra.moviecatalogue.repository.storage.FavoriteService
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_MEDIUM
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_URL
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


class StackRemoteViewsAdapter(
        val context: Context
) : RemoteViewsService.RemoteViewsFactory {
    private val TAG_JOB_WIDGET = "JOB_WIDGET"
    private val mWidgetItems = mutableListOf<MovieFavorite>()
    private val favorite = FavoriteService(context)


    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean  = false

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}

    override fun onDataSetChanged() {
        favorite.findAll(FilmType.MOVIE) {
            mWidgetItems.addAll(it)
        }

        favorite.findAll(FilmType.TVSHOW) {
            mWidgetItems.addAll(it)
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val movie = mWidgetItems[position]
        val rv = RemoteViews(context.packageName, com.yeputra.moviecatalogue.R.layout.widget_item)

        try {
            val imageUrl = URL(POSTER_URL + POSTER_MEDIUM + movie.poster_path)
            val bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
            rv.setImageViewBitmap(com.yeputra.moviecatalogue.R.id.imageView, bitmap)
            rv.setTextViewText(com.yeputra.moviecatalogue.R.id.tv_title, movie.origTitle)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return rv
    }
}