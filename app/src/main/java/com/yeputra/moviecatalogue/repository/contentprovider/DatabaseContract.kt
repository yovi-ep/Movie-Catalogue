package com.yeputra.moviecatalogue.repository.contentprovider

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.yeputra.moviecatalogue"
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
}
