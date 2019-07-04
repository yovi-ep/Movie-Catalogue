package com.yeputra.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val id: Int?,
        val title: String?,
        val overview: String?,
        val release_date: String?,
        val vote_average: Double?,
        val backdrop_path: String?,
        val poster_path: String?,
        val adult: Boolean?,
        val genre_ids: MutableList<Int>?,
        val original_language: String?,
        val original_title: String?,
        val popularity: Double?,
        val video: Boolean?,
        val vote_count: Int?
) : Parcelable

@Parcelize
data class MovieFavorite (
        val id: String?,
        val title: String?,
        val origTitle: String?,
        val overview: String?,
        val adult: Boolean?,
        val release_date: String?,
        val vote_average: String?,
        val backdrop_path: String?,
        val poster_path: String?,
        val type: String?
) : Parcelable {
    companion object {
        const val TABLE_NAME = "FILM_FAVORITE"
        const val FILM_ID = "_ID"
        const val TITLE = "FAV_TITLE"
        const val ORIG_TITLE = "FAV_ORIGTITLE"
        const val OVERVIEW = "FAV_OVERVIEW"
        const val ADULT = "FAV_ADULT"
        const val POSTER = "FAV_POSTER"
        const val BACKDROP = "FAV_BACKDROP"
        const val RELEASE_DATE = "FAV_RELEASE_DATE"
        const val RATE = "FAV_RATE"
        const val TYPE = "FAV_TYPE"
    }
}

enum class FilmType {
    MOVIE, TVSHOW
}