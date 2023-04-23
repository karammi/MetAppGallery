package com.asad.metappgallery.searchScreen.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.GallerySearchError(errorMessage: String, tryAgain: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 80.dp,
                horizontal = 16.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(height = 40.dp))

            OutlinedButton(
                onClick = tryAgain,
                shape = RoundedCornerShape(size = 12.dp),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSecondary),
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 24.dp),
            ) {
                Text(text = "RETRY", color = MaterialTheme.colors.onSecondary)
            }
        }
    }
}
