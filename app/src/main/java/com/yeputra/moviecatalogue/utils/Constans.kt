package com.yeputra.moviecatalogue.utils

interface Constans {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_URL = "https://image.tmdb.org/t/p/"

        const val POSTER_MEDIUM = "/w185/"
        const val POSTER_LARGE = "/w500/"

        const val INTENT_DATA = "INTENT_DATA"
        const val INTENT_DATA_2 = "INTENT_DATA_2"
        const val INTENT_FRAGMENT = "INTENT_FRAGMENT"

        const val CHANGE_LOCAL = 20

        const val DAILY_REMAINDER_TIME = 7
        const val RELEASE_REMAINDER_TIME = 8
    }
}
