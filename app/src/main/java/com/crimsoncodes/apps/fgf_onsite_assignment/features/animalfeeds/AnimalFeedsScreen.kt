package com.crimsoncodes.apps.fgf_onsite_assignment.features.animalfeeds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.crimsoncodes.apps.fgf_onsite_assignment.model.Animal
import com.crimsoncodes.apps.fgf_onsite_assignment.ui.theme.FGFonsiteassignmentTheme
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalFeedsScreen(
    uiState: AnimalFeedsUiState,
    animalDetailsUiState: AnimalDetailsUiState,
    onFavouriteButtonClick: (Boolean, String) -> Unit,
    onDetailsButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier) {
        when (uiState) {
            is AnimalFeedsUiState.EmptyAnimalFeeds -> {}
            is AnimalFeedsUiState.AnimalFeedsData -> {

                val sheetState = rememberModalBottomSheetState()
                var isSheetOpen by rememberSaveable { mutableStateOf(false) }

                val animalImagesItems = uiState.animalImagesItems?.collectAsLazyPagingItems()

                val isRefreshing by remember {
                    derivedStateOf {
                        animalImagesItems?.loadState?.refresh is LoadState.Loading
                    }
                }

                AnimalFeedsList(
                    modifier = Modifier,
                    animalImagesItems = animalImagesItems,
                    isRefreshing = isRefreshing,
                    onFavouriteButtonClick = onFavouriteButtonClick,
                    onDetailsButtonClick = {
                        onDetailsButtonClick(it)
                        isSheetOpen = true
                    }
                )

                if (isSheetOpen) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))

                        CommentsModalBottomSheet(
                            modifier = Modifier,
                            sheetState = sheetState,
                            animalDetailsUiState = animalDetailsUiState,
                            onDismissRequest = { isSheetOpen = false }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalFeedsList(
    modifier: Modifier,
    animalImagesItems: LazyPagingItems<Animal>?,
    isRefreshing: Boolean,
    onFavouriteButtonClick: (Boolean, String) -> Unit,
    onDetailsButtonClick: (String) -> Unit
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { animalImagesItems?.refresh() },
        modifier = modifier,
        state = state
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            animalImagesItems?.let { animalItems ->
                items(
                    animalItems.itemCount,
                    key = animalItems.itemKey { it.id }
                ) { position ->
                    AnimalCard(
                        animal = animalItems[position]!!,
                        onFavouriteButtonClick = onFavouriteButtonClick,
                        onDetailsButtonClick = { onDetailsButtonClick(it) })
                }
            }

            // Loading more
            when (animalImagesItems?.loadState?.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text("Error loading more items")
                    }
                }

                else -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FGFonsiteassignmentTheme {
        val dummySuccessState = AnimalFeedsUiState.AnimalFeedsData(
            animalImagesItems = flowOf(
                PagingData.from(
                    listOf(
                        Animal(id = "1", url = "https://example.com/image1.jpg"),
                        Animal(id = "2", url = "https://example.com/image2.jpg"),
                        Animal(id = "3", url = "https://example.com/image3.jpg"),
                        Animal(id = "4", url = "https://example.com/image3.jpg"),
                        Animal(id = "5", url = "https://example.com/image3.jpg"),
                        Animal(id = "6", url = "https://example.com/image3.jpg"),
                        Animal(id = "7", url = "https://example.com/image3.jpg")
                    )
                )
            )
        )
        AnimalFeedsScreen(dummySuccessState, AnimalDetailsUiState.Loading, { _, _ -> }, {})
    }
}