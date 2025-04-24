package com.crimsoncodes.apps.fgf_onsite_assignment.features.animalfeeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.crimsoncodes.apps.fgf_onsite_assignment.component.MainAsyncImage
import com.crimsoncodes.apps.fgf_onsite_assignment.model.Animal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsModalBottomSheet(
    modifier: Modifier,
    sheetState: SheetState,
    animalDetailsUiState: AnimalDetailsUiState,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        val statusBarHeight = getStatusBarHeight()
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        val usableHeight = screenHeight - statusBarHeight

        Column(
            modifier
                .fillMaxWidth()
                .height(usableHeight)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                when (animalDetailsUiState) {
                    is AnimalDetailsUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is AnimalDetailsUiState.Success -> {
                        val animal = animalDetailsUiState.animal
                        val breed = animal.breeds?.firstOrNull()

                        if (breed != null) {
                            BreedDetailsView(animal)
                        } else {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No breed information found.")
                            }
                        }
                    }
                    is AnimalDetailsUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Failed to load breed details", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BreedDetailsView(animal: Animal) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val breed = animal.breeds!!.first()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            MainAsyncImage(
                imageUrl = animal.url,
                contentDescription = breed.description,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Name: ${breed.name}", style = MaterialTheme.typography.titleLarge)
        Text("Origin: ${breed.origin}")
        Text("Life Span: ${breed.lifeSpan}")
        Text("Temperament: ${breed.temperament}")
        Text("Description: ${breed.description}")
        Text("Affection Level: ${breed.affectionLevel}")
        Text("Intelligence: ${breed.intelligence}")
        Text("Dog Friendly: ${breed.dogFriendly}")
        Text("Child Friendly: ${breed.childFriendly}")

        Spacer(modifier = Modifier.height(8.dp))

        if (!breed.wikipediaUrl.isNullOrEmpty()) {
            val uriHandler = LocalUriHandler.current

            Text(
                text = "More Info: ${breed.wikipediaUrl}",
                color = Color.Blue,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable {
                    uriHandler.openUri(breed.wikipediaUrl)
                }
            )
        }
    }
}

@Composable
fun getStatusBarHeight(): Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
}
