package com.asad.metappgallery.searchScreen.presentation.util

import androidx.compose.ui.unit.dp
import com.asad.metappgallery.core.util.Emoji

object UiConstant {

    val HEADER_DEFAULT_HEIGHT =
        64.dp // According to Materia // l Design 3: https://m3.material.io/components/top-app-bar/specs#e3fd3eba-0444-437c-9a82-071ef03d85b1

    //region title
    val GallerySearchEmptyStateTitle = "Let's ${Emoji.eye} Museum of Art Gallery ${Emoji.blackCat}"

    const val QuerySearchTextFieldValue = "Start searching..."
    const val QuerySearchText = "sunflower"
    //endregion

    //region tags or semantic contentDescription for ui test
    const val GalleryEmptyContent = "empty_content"
    const val SearchGalleryTextField = "search_text_field"
    const val QuerySearchIcon = "search_icon"

    const val GalleryGridContentDescription: String = "gallery_search_grid"
    const val GalleryCardContentDescription: String = "gallery_search_card"

    const val ItemFirstContentDescription: String = "item_1"
    const val ItemSecondContentDescription: String = "item_2"
    //endregion
}
