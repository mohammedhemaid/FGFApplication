package com.crimsoncodes.apps.fgf_onsite_assignment.features.animalfeeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crimsoncodes.apps.fgf_onsite_assignment.data.AnimalDetailsRepository
import com.crimsoncodes.apps.fgf_onsite_assignment.data.AnimalFeedsRepository
import com.crimsoncodes.apps.fgf_onsite_assignment.data.asResult
import com.crimsoncodes.apps.fgf_onsite_assignment.model.Animal
import com.crimsoncodes.apps.fgf_onsite_assignment.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AnimalFeedsViewModel @Inject constructor(
    private val animalFeedsRepository: AnimalFeedsRepository,
    private val animalDetailsRepository: AnimalDetailsRepository
) : ViewModel() {

    private val animalFeedsFlow: Flow<PagingData<Animal>> = animalFeedsRepository
        .getFeedsPagingSource()
        .cachedIn(viewModelScope)

    val feedsUiState: StateFlow<AnimalFeedsUiState> = flow {
        emit(AnimalFeedsUiState.AnimalFeedsData(animalFeedsFlow))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        AnimalFeedsUiState.EmptyAnimalFeeds
    )

    private val _animalId = MutableStateFlow<String?>(null)

    val animalDetailsUiState: StateFlow<AnimalDetailsUiState> = _animalId
        .filterNotNull()
        .flatMapLatest { id ->
            animalDetailsRepository.getPhotoDetails(id).asResult()
                .map { result ->
                    when (result) {
                        is Result.Success -> AnimalDetailsUiState.Success(result.data)
                        is Result.Loading -> AnimalDetailsUiState.Loading
                        is Result.Error -> AnimalDetailsUiState.Error
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AnimalDetailsUiState.Loading
        )

    fun getAnimalDetails(animalId: String) {
        _animalId.value = animalId
    }

    fun setFavourite(isChecked:Boolean, photoId: String) {
        viewModelScope.launch {
            animalFeedsRepository.setFavoritePhoto(photoId, isChecked)
        }
    }
}

sealed interface AnimalFeedsUiState {
    data class AnimalFeedsData(
        val animalImagesItems: Flow<PagingData<Animal>>? = null,
    ) : AnimalFeedsUiState
    data object EmptyAnimalFeeds : AnimalFeedsUiState
}

sealed interface AnimalDetailsUiState {
    data class Success(val animal: Animal) : AnimalDetailsUiState
    data object Error : AnimalDetailsUiState
    data object Loading : AnimalDetailsUiState
}
