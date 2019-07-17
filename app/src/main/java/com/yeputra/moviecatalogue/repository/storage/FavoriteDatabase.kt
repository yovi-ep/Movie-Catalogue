package com.yeputra.moviecatalogue.repository.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.yeputra.moviecatalogue.model.MovieFavorite
import com.yeputra.moviecatalogue.utils.Constans.Companion.DB_FAVORITE
import org.jetbrains.anko.db.*

class FavoriteDatabase(
    context: Context
): ManagedSQLiteOpenHelper (
    context, DB_FAVORITE, null, 2
) {
    companion object {
        private var instance: FavoriteDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context) : FavoriteDatabase = (instance ?: run {
            instance = FavoriteDatabase(ctx)
            instance
        }) as FavoriteDatabase
    }

    override fun onCreate(db: SQLiteDatabase) {
        MovieFavorite.apply {
            db.createTable(TABLE_NAME, true,
                    FILM_ID to TEXT + PRIMARY_KEY,
                    TITLE to TEXT,
                    ORIG_TITLE to TEXT,
                    OVERVIEW to TEXT,
                    ADULT to INTEGER,
                    RELEASE_DATE to TEXT,
                    RATE to TEXT,
                    BACKDROP to TEXT,
                    POSTER to TEXT,
                    TYPE to TEXT)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MovieFavorite.TABLE_NAME, true)
    }
}

