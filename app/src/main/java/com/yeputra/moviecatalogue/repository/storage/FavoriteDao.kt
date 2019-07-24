package com.yeputra.moviecatalogue.repository.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeputra.moviecatalogue.model.MovieFavorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favorite: MovieFavorite)

    @Query("DELETE FROM ${MovieFavorite.TABLE_NAME} WHERE id = :id")
    fun delete(id: String)

    @Query("SELECT * from ${MovieFavorite.TABLE_NAME} WHERE type = :filmType")
    fun findAll(filmType: String) : LiveData<MutableList<MovieFavorite>>

    @Query("SELECT * from ${MovieFavorite.TABLE_NAME} WHERE id = :id")
    fun findOne(id: String) : LiveData<MovieFavorite>

}