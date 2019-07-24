package com.yeputra.moviecatalogue.repository.storage

import androidx.lifecycle.LiveData
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.MovieFavorite

class FavoriteRepository(
        private val favoriteDao: FavoriteDao
) {

    val allMovie = favoriteDao.findAll(FilmType.MOVIE.name)
    val allTVShow = favoriteDao.findAll(FilmType.TVSHOW.name)

    fun add(favorite: MovieFavorite) {
        favoriteDao.add(favorite)
    }

    fun delete(id: String) {
        favoriteDao.delete(id)
    }

    fun findOne(id: String) : LiveData<MovieFavorite>  = favoriteDao.findOne(id)
}