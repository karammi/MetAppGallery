package com.asad.metappgallery.searchScreen.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant

@Composable
fun BoxScope.CustomEmptyContent(body: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 80.dp,
                horizontal = 16.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = body,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .semantics {
                    contentDescription = UiConstant.GalleryEmptyContent
                },

        )
    }
}
