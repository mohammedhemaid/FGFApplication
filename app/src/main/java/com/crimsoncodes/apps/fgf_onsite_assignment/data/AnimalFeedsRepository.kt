package com.crimsoncodes.apps.fgf_onsite_assignment.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.crimsoncodes.apps.fgf_onsite_assignment.database.AppDatabase
import com.crimsoncodes.apps.fgf_onsite_assignment.database.model.asExternalModel
import com.crimsoncodes.apps.fgf_onsite_assignment.model.Animal
import com.crimsoncodes.apps.fgf_onsite_assignment.network.ServiceAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimalFeedsRepository  @Inject constructor(
    private val service: ServiceAPI,
    private val appDatabase: AppDatabase,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getFeedsPagingSource(): Flow<PagingData<Animal>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = AppRemoteMediator(
                service,
                appDatabase
            ),
            pagingSourceFactory = { appDatabase.animalDao().getAllAnimals() }
        ).flow.map { pagingData ->
            pagingData.map { animalEntity ->
                animalEntity.asExternalModel()
            }
        }
    }

    suspend fun setFavoritePhoto(photoId: String, isFavorite: Boolean) {
        appDatabase.animalDao().updateFavorite(photoId, isFavorite)

    }
}