package com.crimsoncodes.apps.fgf_onsite_assignment.features.animalfeeds

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crimsoncodes.apps.fgf_onsite_assignment.R
import com.crimsoncodes.apps.fgf_onsite_assignment.component.IconButton
import com.crimsoncodes.apps.fgf_onsite_assignment.component.IconToggleButton
import com.crimsoncodes.apps.fgf_onsite_assignment.component.MainAsyncImage
import com.crimsoncodes.apps.fgf_onsite_assignment.model.Animal
import com.crimsoncodes.apps.fgf_onsite_assignment.ui.theme.FGFonsiteassignmentTheme

@Composable
fun AnimalCard(
    animal: Animal,
    onFavouriteButtonClick: (Boolean, String) -> Unit,
    onDetailsButtonClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center,
            ) {
                MainAsyncImage(
                    imageUrl = animal.url,
                    contentDescription = "Animal image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

            }

            Spacer(modifier = Modifier.weight(1f))

            ActionSection(
                isFavourite = animal.isFavourite,
                onFavouriteButtonClick = { isChecked ->
                    onFavouriteButtonClick(
                        isChecked,
                        animal.id
                    )
                },
                onDetailsButtonClick = { onDetailsButtonClick(animal.id) })
        }

    }
}

@Composable
fun ActionSection(
    isFavourite: Boolean,
    onFavouriteButtonClick: (Boolean) -> Unit,
    onDetailsButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        var firstChecked by rememberSaveable { mutableStateOf(isFavourite) }
        IconToggleButton(
            checked = firstChecked,
            onCheckedChange = { checked ->
                onFavouriteButtonClick(checked)
                firstChecked = checked
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                )
            },
        )

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onButtonClick = onDetailsButtonClick,
            text = R.string.comments,
            icon = R.drawable.ic_comments
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RowPreview() {
    FGFonsiteassignmentTheme {
        AnimalCard(Animal("Animal Name", ""), { _, _ -> }, {})
    }
}