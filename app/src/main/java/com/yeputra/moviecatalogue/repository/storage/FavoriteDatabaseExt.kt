package com.yeputra.moviecatalogue.repository.storage

import android.content.Context


val Context.database: FavoriteDatabase
    get() = FavoriteDatabase.getInstance(this)
