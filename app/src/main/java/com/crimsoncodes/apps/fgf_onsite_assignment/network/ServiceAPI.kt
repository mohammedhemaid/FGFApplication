package com.crimsoncodes.apps.fgf_onsite_assignment.network

import com.crimsoncodes.apps.fgf_onsite_assignment.network.model.AnimalImagesItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceAPI {
    @GET("images/search")
    suspend fun getAnimalData(
        @Query("page") page: Int = 0,
        @Query("limit") perPage: Int = 20,
        @Query("has_breeds") hasBreeds: Boolean = true
    ): List<AnimalImagesItem>

    @GET("images/{image_id}")
    suspend fun getAnimalDetails(
        @Path("image_id") imageId: String,
    ): AnimalImagesItem
}
