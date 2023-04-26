package com.asad.metappgallery.searchScreen.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant

@Composable
fun GallerySearchData(data: List<Int>, onItemClicked: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp, top = 16.dp)
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GallerySearchDataPreview() {
    GallerySearchData(data = listOf(1, 2, 3), onItemClicked = {})
}
