package com.yeputra.moviecatalogue.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val id: Int?,
        @SerializedName("title", alternate = ["name"])
        val title: String?,
        val overview: String?,
        val release_date: String?,
        val vote_average: Double?,
        val backdrop_path: String?,
        val poster_path: String?,
        val adult: Boolean?,
        val genre_ids: MutableList<Int>?,
        val original_language: String?,
        @SerializedName("original_title", alternate = ["original_name"])
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
) : Parcelable

enum class FilmType {
    MOVIE, TVSHOW
}