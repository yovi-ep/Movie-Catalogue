package com.yeputra.moviecatalogue.repository.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yeputra.moviecatalogue.model.MovieFavorite

@Database(entities = [MovieFavorite::class], version = 1)
abstract class MyFavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: MyFavoriteDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MyFavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyFavoriteDatabase::class.java,
                        "film_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}