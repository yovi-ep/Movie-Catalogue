package com.yeputra.moviecatalogue.repository.contentprovider

import android.content.Context
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.MovieFavorite

class FavoriteService(val context: Context) {
    private var favoriteHelper = FavoriteHelper.getInstance(context)
    fun add(favorite: MovieFavorite) {
        favoriteHelper.open()
        favoriteHelper.insert(favorite)
        favoriteHelper.close()
    }

    fun delete(id: String) {
        favoriteHelper.open()
        favoriteHelper.delete(id)
        favoriteHelper.close()
    }

    fun findAll(type: FilmType, listener: (MutableList<MovieFavorite>) -> Unit) {
        favoriteHelper.open()
        listener(favoriteHelper.query(type.name))
        favoriteHelper.close()
    }

    fun findOne(id: String, listener: (MovieFavorite?) -> Unit){
        favoriteHelper.open()
        listener(favoriteHelper.queryById(id))
        favoriteHelper.close()
    }
}