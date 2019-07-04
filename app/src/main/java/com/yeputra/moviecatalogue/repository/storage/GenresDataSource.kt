package com.yeputra.moviecatalogue.repository.storage

import android.annotation.SuppressLint
import android.content.Context
import com.yeputra.moviecatalogue.R

class GenresDataSource (val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: GenresDataSource? = null

        @Synchronized
        fun getInstance(ctx: Context): GenresDataSource {
            if (instance == null) {
                instance = GenresDataSource(ctx.applicationContext)
            }
            return instance!!
        }
    }

    val movieGenres: MutableMap<String, String> by lazy {
        mutableMapOf(
                Pair("28", getString(R.string.app_name)),
                Pair("12", getString(R.string.app_name)),
                Pair("16", getString(R.string.app_name)),
                Pair("35", getString(R.string.app_name)),
                Pair("80", getString(R.string.app_name)),
                Pair("99", getString(R.string.app_name)),
                Pair("18", getString(R.string.app_name)),
                Pair("10751", getString(R.string.app_name)),
                Pair("14", getString(R.string.app_name)),
                Pair("36", getString(R.string.app_name)),
                Pair("27", getString(R.string.app_name)),
                Pair("10402", getString(R.string.app_name)),
                Pair("9648", getString(R.string.app_name)),
                Pair("10749", getString(R.string.app_name)),
                Pair("878", getString(R.string.app_name)),
                Pair("10770", getString(R.string.app_name)),
                Pair("53", getString(R.string.app_name)),
                Pair("10752", getString(R.string.app_name)),
                Pair("37", getString(R.string.app_name))
        )
    }


    private fun getString(id: Int) : String = context.getString(id)
}