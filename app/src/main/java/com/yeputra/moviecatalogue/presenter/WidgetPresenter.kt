package com.yeputra.moviecatalogue.presenter

import android.content.Context
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.MovieFavorite
import com.yeputra.moviecatalogue.repository.contentprovider.FavoriteService

class WidgetPresenter(val context: Context) {
    private val favoriteService = FavoriteService(context)

    fun getWidgetContent() : MutableList<MovieFavorite> {
        val datas = mutableListOf<MovieFavorite>()

        favoriteService.findAll(FilmType.MOVIE) {
            datas.addAll(it)
        }

        favoriteService.findAll(FilmType.TVSHOW) {
            datas.addAll(it)
        }

        return datas
    }
}