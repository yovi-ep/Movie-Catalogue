package com.yeputra.moviecatalogue.repository.contentprovider

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.yeputra.moviecatalogue.database"
    private const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "FILM_FAVORITE"

            const val TITLE = "FAV_TITLE"
            const val ORIG_TITLE = "FAV_ORIGTITLE"
            const val OVERVIEW = "FAV_OVERVIEW"
            const val ADULT = "FAV_ADULT"
            const val POSTER = "FAV_POSTER"
            const val BACKDROP = "FAV_BACKDROP"
            const val RELEASE_DATE = "FAV_RELEASE_DATE"
            const val RATE = "FAV_RATE"
            const val TYPE = "FAV_TYPE"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }

    fun getColumnString(cursor: Cursor, columnName: String): String {
        return cursor.getString(cursor.getColumnIndex(columnName))
    }

    fun getColumnInt(cursor: Cursor, columnName: String): Int {
        return cursor.getInt(cursor.getColumnIndex(columnName))
    }

    fun getColumnLong(cursor: Cursor, columnName: String): Long {
        return cursor.getLong(cursor.getColumnIndex(columnName))
    }
}
