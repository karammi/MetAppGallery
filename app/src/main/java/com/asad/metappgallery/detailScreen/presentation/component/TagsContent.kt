package com.asad.metappgallery.detailScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.detailScreen.data.model.TagModel

@Composable
fun TagsContent(tags: List<TagModel>) {
    val horizontalScrollState = rememberScrollState()
    val surfaceColor = MaterialTheme.colors.surface
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(surfaceColor)
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .horizontalScroll(horizontalScrollState),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        tags.map {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(24.dp),
                    textAlign = TextAlign.Center,
                    text = "#${it.term}",
                    style = TextStyle(textAlign = TextAlign.Center, color = Color.Black),
                )
            }
        }
    }
}
