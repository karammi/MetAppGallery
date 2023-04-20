package com.asad.metappgallery.detailScreen.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.core.presentation.CustomNetworkImage

@Composable
fun ObjectAdditionalImageContent(images: List<String>, backgroundColor: Color) {
    val horizontalScrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(200.dp)
            .background(backgroundColor)
            .horizontalScroll(state = horizontalScrollState)
            .padding(horizontal = 24.dp),
    ) {
        images.map {
            Card(
                modifier = Modifier
                    .requiredHeight(200.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                backgroundColor = Color.LightGray,
            ) {
                CustomNetworkImage(
                    url = it,
                    contentScale = ContentScale.Fit,
                    imageModifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(4.dp)),
                )
            }
        }
    }
}
