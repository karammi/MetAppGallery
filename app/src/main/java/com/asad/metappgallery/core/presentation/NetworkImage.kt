package com.asad.metappgallery.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    imageModifier: Modifier = Modifier,
    errorBuilder: (@Composable () -> Unit)? = null,
    loadingBuilder: (@Composable () -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val imageBuilder = ImageRequest.Builder(LocalContext.current)
            .data(data = url)
            .size(size = Size.ORIGINAL)
            .diskCacheKey(key = url)
            .memoryCacheKey(key = url)
            .crossfade(enable = true)
            .crossfade(durationMillis = 100)

        val painter = rememberAsyncImagePainter(
            model = imageBuilder.build(),
            contentScale = ContentScale.Fit,
        )

        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = imageModifier,
            contentScale = contentScale,
            alignment = alignment,
        )

        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                when {
                    loadingBuilder != null -> loadingBuilder()
                    else -> LinearProgressIndicator(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 8.dp)
                            .align(Alignment.Center),
                        color = Color.Black,
                        backgroundColor = Color.Black.copy(0.2f),
                    )
                }
            }
            is AsyncImagePainter.State.Error -> {
                when {
                    errorBuilder != null -> errorBuilder()
                    else -> {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            tint = Color.Red,
                            modifier = Modifier.requiredSize(24.dp),
                            contentDescription = null,
                        )
                    }
                }
            }
            else -> {}
        }
    }
}
