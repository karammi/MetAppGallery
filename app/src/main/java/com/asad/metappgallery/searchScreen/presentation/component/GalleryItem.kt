package com.asad.metappgallery.searchScreen.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GalleryItem(title: String, onItemClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onItemClicked.invoke(title.toInt()) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .semantics {
                contentDescription = "gallery_search_card"
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .semantics {
                    contentDescription = "item_$title"
                },
            textAlign = TextAlign.Center,
        )
    }
}
