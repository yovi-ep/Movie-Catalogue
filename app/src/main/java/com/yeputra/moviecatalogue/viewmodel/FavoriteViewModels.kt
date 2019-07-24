package com.yeputra.moviecatalogue.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.yeputra.moviecatalogue.base.BaseViewModel
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.model.MovieFavorite
import com.yeputra.moviecatalogue.model.MovieResponse
import com.yeputra.moviecatalogue.repository.storage.FavoriteRepository
import com.yeputra.moviecatalogue.repository.storage.MyFavoriteDatabase
import com.yeputra.moviecatalogue.utils.RxUtils
import io.reactivex.Observable

class FavoriteViewModels(app: Application) : BaseViewModel(app) {
    private val repository: FavoriteRepository
    val allMovie: LiveData<MutableList<MovieFavorite>>
    val allTVShow: LiveData<MutableList<MovieFavorite>>

    init {
        val dao = MyFavoriteDatabase.getDatabase(app).favoriteDao()
        repository = FavoriteRepository(dao)
        allMovie = repository.allMovie
        allTVShow = repository.allTVShow
    }

    fun add(movie: Movie, type: FilmType) {
        subscriber = Observable.fromCallable {
            repository.add(MovieFavorite(
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
            ))}
                .compose(RxUtils.applyObservableAsync())
                .subscribe()
    }

    fun delete(id: String) {
        subscriber = Observable
                .fromCallable { repository.delete(id) }
                .compose(RxUtils.applyObservableAsync())
                .subscribe()

    }

    fun findOne(id: String) : LiveData<MovieFavorite>? {
        return repository.findOne(id)
    }

    fun convertFavorite(data: MutableList<MovieFavorite>) : MovieResponse {
        val result = mutableListOf<Movie>()
        data.forEach {
            result.add(Movie(
                    it.id.toInt(),
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
        return MovieResponse(0, result, 0, 0)
    }

    override fun onResponseSuccess(data: Any) {}
}