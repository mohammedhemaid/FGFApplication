package com.crimsoncodes.apps.fgf_onsite_assignment.component

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crimsoncodes.apps.fgf_onsite_assignment.ui.theme.Pink80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    @StringRes title: Int,
    elevation: Dp = 0.dp
) {
    Surface(shadowElevation = elevation) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(title),
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Pink80
            ),
        )
    }
}