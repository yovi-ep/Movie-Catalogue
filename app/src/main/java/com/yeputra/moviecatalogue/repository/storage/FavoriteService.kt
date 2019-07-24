package com.yeputra.moviecatalogue.repository.storage

import android.content.Context
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.MovieFavorite
import com.yeputra.moviecatalogue.repository.contentprovider.FavoriteHelper

class FavoriteService(val context: Context) {
    private var favoriteHelper = FavoriteHelper.getInstance(context)
    fun add(favorite: MovieFavorite) {
        favoriteHelper.open()
        favoriteHelper.insert(favorite)
        favoriteHelper.close()
        /*context.database.use {
            insert(MovieFavorite.TABLE_NAME,
                    MovieFavorite.FILM_ID to favorite.id,
                    MovieFavorite.TITLE to favorite.title,
                    MovieFavorite.ORIG_TITLE to favorite.origTitle,
                    MovieFavorite.OVERVIEW to favorite.overview,
                    MovieFavorite.ADULT to favorite.adult,
                    MovieFavorite.POSTER to favorite.poster_path,
                    MovieFavorite.BACKDROP to favorite.backdrop_path,
                    MovieFavorite.RELEASE_DATE to favorite.release_date,
                    MovieFavorite.RATE to favorite.vote_average,
                    MovieFavorite.TYPE to favorite.type)
        }*/
    }

    fun delete(id: String) {
        favoriteHelper.open()
        favoriteHelper.delete(id)
        favoriteHelper.close()
        /*context.database.use {
            delete(MovieFavorite.TABLE_NAME,
                    MovieFavorite.FILM_ID + "={id}",
                    "id" to id)
        }*/
    }

    fun findAll(type: FilmType, listener: (MutableList<MovieFavorite>) -> Unit) {
        favoriteHelper.open()
        listener(favoriteHelper.query(type.name))
        favoriteHelper.close()
        /*context.database.use {
            val data = mutableListOf<MovieFavorite>()
            val result = select(MovieFavorite.TABLE_NAME)
                    .whereArgs(MovieFavorite.TYPE + "={type}",
                            "type" to type.name)
            data.addAll(result.parseList(classParser()))
            listener(data)
        }*/
    }

    fun findOne(id: String, listener: (MovieFavorite?) -> Unit){
        favoriteHelper.open()
        listener(favoriteHelper.queryById(id))
        favoriteHelper.close()
        /*context.database.use {
            val result = select(MovieFavorite.TABLE_NAME)
                    .whereArgs(MovieFavorite.FILM_ID + "={id}",
                            "id" to id)
            val fav = result.parseList(classParser<MovieFavorite>())

            if (fav.isNullOrEmpty()) listener(null)
            else listener(fav[0])
        }*/
    }
}