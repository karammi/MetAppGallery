package com.asad.metappgallery.galleryFinder.presentation.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.ErrorResultContent(errorMessage: String) {
    Text(
        text = errorMessage,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        color = Color.Red,
        modifier = Modifier
            .align(alignment = Alignment.Center)
            .padding(horizontal = 16.dp),
    )
}