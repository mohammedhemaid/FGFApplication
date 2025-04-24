package com.crimsoncodes.apps.fgf_onsite_assignment.model

import com.crimsoncodes.apps.fgf_onsite_assignment.network.model.Breed

data class Animal(
    val id: String,
    val url: String,
    val width: Int? = 0,
    val height: Int? = 0,
    val breeds: List<Breed>? = emptyList(),
    val isFavourite: Boolean = false
)