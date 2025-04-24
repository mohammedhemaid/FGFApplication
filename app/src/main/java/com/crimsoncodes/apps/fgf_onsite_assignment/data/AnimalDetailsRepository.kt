package com.crimsoncodes.apps.fgf_onsite_assignment.data

import com.crimsoncodes.apps.fgf_onsite_assignment.network.ServiceAPI
import com.crimsoncodes.apps.fgf_onsite_assignment.network.model.asExternalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnimalDetailsRepository @Inject constructor(
    private val service: ServiceAPI
) {
    fun getPhotoDetails(imageId: String) = flow {
        emit(service.getAnimalDetails(imageId).asExternalModel())
    }.flowOn(Dispatchers.IO)
}