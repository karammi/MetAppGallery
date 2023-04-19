package com.asad.metappgallery.searchScreen.data.adapter

import com.asad.metappgallery.core.util.Emoji

object GalleryResponseConstant {
    const val Total: String = "total"
    const val ObjectIDs: String = "objectIDs"

    val GalleryCollectionApi: (String) -> String = { queryString ->
        "https://collectionapi.metmuseum.org/public/collection/v1/search?q=$queryString"
    }

    val FailedToFetchDataDefaultMessage =
        Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed to fetch data, please try Again"
}
