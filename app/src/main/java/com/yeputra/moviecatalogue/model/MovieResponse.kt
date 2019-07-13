package com.yeputra.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
        val page: Int?,
        val results: MutableList<Movie>?,
        val total_pages: Int?,
        val total_results: Int?
) : Parcelable

@Parcelize
data class SearchResponse(
        val page: Int?,
        val results: MutableList<Movie>?,
        val total_pages: Int?,
        val total_results: Int?
) : Parcelable