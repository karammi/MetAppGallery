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
fun BoxScope.EmptyResultContent() {
    Text(
        text = "Empty state",
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        color = Color.Blue,
        modifier = Modifier
            .align(alignment = Alignment.Center)
            .padding(horizontal = 16.dp),
    )
}
