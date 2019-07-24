package com.yeputra.moviecatalogue.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.yeputra.moviecatalogue.model.MovieFavorite.Companion.TABLE_NAME
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
@Entity(tableName = TABLE_NAME)
data class MovieFavorite (
        @PrimaryKey
        val id: String,
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
    }
}

enum class FilmType {
    MOVIE, TVSHOW
}