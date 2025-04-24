package com.crimsoncodes.apps.fgf_onsite_assignment.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crimsoncodes.apps.fgf_onsite_assignment.database.model.AnimalEntity

@Dao
interface AnimalDao {

    @Query("SELECT * FROM animal")
    fun getAllAnimals() : PagingSource<Int, AnimalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photoEntity: List<AnimalEntity>)

    @Query("UPDATE animal SET isFavourite = :isFavorite WHERE id = :photoId")
    suspend fun updateFavorite(photoId: String, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM animal")
    suspend fun countPhotos(): Int

    @Query("DELETE FROM animal")
    suspend fun clearPhotos()
}