package com.crimsoncodes.apps.fgf_onsite_assignment.network.model

import com.crimsoncodes.apps.fgf_onsite_assignment.database.model.AnimalEntity
import com.crimsoncodes.apps.fgf_onsite_assignment.model.Animal

data class AnimalImagesItem(
    val breeds: List<Breed>? = emptyList(),
    val favourite: Favourite? = null,
    val height: Int = 0,
    val id: String = "",
    val url: String = "",
    val width: Int = 0
)

fun AnimalImagesItem.asEntity() = AnimalEntity(
    id = id,
    url = url,
    width = width,
    height = height,
    isFavourite = false
)

fun AnimalImagesItem.asExternalModel() = Animal(
    id = id,
    url = url,
    width = width,
    height = height,
    breeds = breeds
)
