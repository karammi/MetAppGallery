package com.asad.metappgallery.detailScreen.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.core.util.ComposeUtil
import com.asad.metappgallery.detailScreen.presentation.component.ObjectDetailContent
import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailScreen
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
import com.asad.metappgallery.searchScreen.presentation.component.CustomAppBar
import com.asad.metappgallery.searchScreen.presentation.component.CustomEmptyContent
import com.asad.metappgallery.searchScreen.presentation.component.CustomTouchableScale

private const val TAG = "DetailScreen"

@Composable
fun ObjectDetailScreen(
    viewModel: ObjectDetailViewModel,
    currentObjectId: String,
    onNavigationBack: () -> Unit,
) {
    BackHandler {
        viewModel.cancelFetchingData()
        onNavigationBack.invoke()
    }

    // This is supposed the be run only once
    LaunchedEffect(key1 = true) {
        viewModel.fetchObjectDetail(currentObjectId.toInt())
    }
    val uiState = ComposeUtil.rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center,
    ) {
        when (uiState.value.objectDetailState) {
            UiState.Empty -> {
                CustomEmptyContent(body = ObjectDetailScreen.GalleryDataEmptyValue)
            }

            UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .requiredSize(48.dp)
                        .align(Alignment.Center),
                    color = Color.White,
                    strokeWidth = 4.dp,
                )
            }

            is UiState.Success -> {
                val data = uiState.value.objectDetailState.data!!
                ObjectDetailContent(data = data)
            }

            is UiState.Error -> {
                CustomTouchableScale(onClick = {
                    viewModel.fetchObjectDetail(currentObjectId.toInt())
                }) {
                    val errorMessage =
                        uiState.value.objectDetailState.error
                            ?: ObjectDetailScreen.GalleryErrorTitle

                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.h6,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 24.dp),
                    )
                }
            }
        }

        CustomAppBar(
            title = uiState.value.objectDetailState.data?.objectName
                ?: ObjectDetailScreen.GalleryDetail,
            onNavigateUp = onNavigationBack,
            isTransparent = true,
        )
    }
}
