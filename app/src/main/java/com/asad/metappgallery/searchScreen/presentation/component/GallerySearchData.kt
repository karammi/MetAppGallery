package com.asad.metappgallery.searchScreen.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GallerySearchData(data: List<Int>, onItemClicked: (Int) -> Unit) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp),
        modifier = Modifier
            .padding(bottom = 80.dp)
            .semantics {
                contentDescription = UiConstant.GalleryGridContentDescription
            },
    ) {
        items(data.size) { index ->
            GalleryItem(
                title = data[index].toString(),
                onItemClicked = onItemClicked,
            )
        }
    }
}
