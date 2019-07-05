package com.yeputra.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yeputra.moviecatalogue.base.BaseViewModel
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.model.MovieFavorite
import com.yeputra.moviecatalogue.repository.storage.FavoriteService

class FavoriteViewModel : BaseViewModel() {
    private val movieLiveData = MutableLiveData<MutableList<Movie>>()
    private val tvLiveData = MutableLiveData<MutableList<Movie>>()
    private val favoriteLiveData = MutableLiveData<Boolean>()

    private lateinit var favoriteService: FavoriteService

    fun add(movie: Movie, type: FilmType) {
        view?.contextView()?.let {
            favoriteService = FavoriteService(it)
            favoriteService.add(
                    MovieFavorite(
                            movie.id.toString(),
                            movie.title,
                            movie.original_title,
                            movie.overview,
                            movie.adult,
                            movie.release_date,
                            movie.vote_average.toString(),
                            movie.backdrop_path,
                            movie.poster_path,
                            type.name
                    )
            )
            favoriteLiveData.postValue(true)

            when (type) {
                FilmType.MOVIE -> getMovieFavorite()
                FilmType.TVSHOW -> getTvFavorite()
            }
        }
    }

    fun delete(filmId: String) {
        view?.contextView()?.let {
            favoriteService = FavoriteService(it)
            favoriteService.delete(filmId)
            favoriteLiveData.postValue(false)
        }
    }

    fun isFavorite(filmId: String) : LiveData<Boolean> {
        view?.contextView()?.let {
            favoriteService = FavoriteService(it)
            favoriteService.findOne(filmId) { data: MovieFavorite? ->
                data?.let {
                    favoriteLiveData.postValue(true)
                }?: run {
                    favoriteLiveData.postValue(false)
                }
            }
        }
        return favoriteLiveData
    }

    fun getMovieFavorite() : LiveData<MutableList<Movie>> {
        view?.onShowProgressbar()
        view?.contextView()?.let {
            favoriteService = FavoriteService(it)
            favoriteService.findAll(FilmType.MOVIE) { data ->
                movieLiveData.postValue(convertFavorite(data))
            }
        }
        return movieLiveData
    }

    fun getTvFavorite() : LiveData<MutableList<Movie>> {
        view?.onShowProgressbar()
        view?.contextView()?.let {
            favoriteService = FavoriteService(it)
            favoriteService.findAll(FilmType.TVSHOW) { data ->
                tvLiveData.postValue(convertFavorite(data))
            }
        }
        return tvLiveData
    }

    private fun convertFavorite(data: MutableList<MovieFavorite>) : MutableList<Movie> {
        val result = mutableListOf<Movie>()
        data.forEach {
            result.add(Movie(
                    it.id?.toInt(),
                    it.title,
                    it.overview,
                    it.release_date,
                    it.vote_average?.toDouble(),
                    it.backdrop_path,
                    it.poster_path,
                    it.adult,
                    null,
                    "",
                    it.origTitle,
                    0.0,
                    false,
                    0
            ))
        }
        return result
    }
    override fun onResponseSuccess(data: Any) {}
}