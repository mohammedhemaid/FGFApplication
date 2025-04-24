package com.crimsoncodes.apps.fgf_onsite_assignment.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crimsoncodes.apps.fgf_onsite_assignment.model.Animal

@Entity(tableName = "animal")
data class AnimalEntity(
    @PrimaryKey val id: String,
    val url: String,
    val width: Int,
    val height: Int?,
    val isFavourite: Boolean
)

fun AnimalEntity.asExternalModel() = Animal(
    id = id,
    url = url,
    width = width,
    height = height,
    isFavourite = isFavourite
)