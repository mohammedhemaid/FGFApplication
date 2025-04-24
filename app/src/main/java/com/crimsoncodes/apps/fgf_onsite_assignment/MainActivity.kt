package com.crimsoncodes.apps.fgf_onsite_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crimsoncodes.apps.fgf_onsite_assignment.component.AppTopBar
import com.crimsoncodes.apps.fgf_onsite_assignment.features.animalfeeds.AnimalFeedsScreen
import com.crimsoncodes.apps.fgf_onsite_assignment.features.animalfeeds.AnimalFeedsUiState
import com.crimsoncodes.apps.fgf_onsite_assignment.features.animalfeeds.AnimalFeedsViewModel
import com.crimsoncodes.apps.fgf_onsite_assignment.ui.theme.FGFonsiteassignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FGFonsiteassignmentTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets.safeDrawing,
                    topBar = { AppTopBar(title = R.string.app_name) }) { paddingValues ->
                    Surface(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                        val viewModel = hiltViewModel<AnimalFeedsViewModel>()
                        val state by viewModel.feedsUiState.collectAsStateWithLifecycle()
                        val animalDetailsUiState by viewModel.animalDetailsUiState.collectAsStateWithLifecycle()
                        AnimalFeedsScreen(
                            uiState = state,
                            animalDetailsUiState = animalDetailsUiState,
                            onFavouriteButtonClick = viewModel::setFavourite,
                            onDetailsButtonClick = viewModel::getAnimalDetails
                        )
                    }
                }
            }
        }
    }
}
