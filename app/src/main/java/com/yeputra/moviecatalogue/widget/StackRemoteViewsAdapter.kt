package com.yeputra.moviecatalogue.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.model.MovieFavorite
import com.yeputra.moviecatalogue.repository.storage.FavoriteRepository
import com.yeputra.moviecatalogue.repository.storage.MyFavoriteDatabase
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_MEDIUM
import com.yeputra.moviecatalogue.utils.Constans.Companion.POSTER_URL
import java.net.URL


class StackRemoteViewsAdapter(
        val context: Context
) : RemoteViewsService.RemoteViewsFactory {
    private val TAG = StackRemoteViewsAdapter::class.java.simpleName
    private val mWidgetItems = mutableListOf<MovieFavorite>()
    private lateinit var favoriteRepository: FavoriteRepository

    override fun onCreate() {
        val dao = MyFavoriteDatabase.getDatabase(context).favoriteDao()
        favoriteRepository = FavoriteRepository(dao)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean  = false

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}

    override fun onDataSetChanged() {
        mWidgetItems.clear()
        favoriteRepository.allMovie.value?.let { mWidgetItems.addAll(it) }
        favoriteRepository.allTVShow.value?.let { mWidgetItems.addAll(it) }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val movie = mWidgetItems[position]
        val rv = RemoteViews(context.packageName, R.layout.widget_item)

        try {
            val imageUrl = URL(POSTER_URL + POSTER_MEDIUM + movie.poster_path)
            val bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
            rv.setImageViewBitmap(R.id.imageView, bitmap)
            rv.setTextViewText(R.id.tv_title, movie.origTitle)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return rv
    }
}