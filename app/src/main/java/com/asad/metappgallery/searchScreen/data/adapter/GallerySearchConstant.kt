package com.asad.metappgallery.searchScreen.data.adapter

import com.asad.metappgallery.core.util.Emoji

private const val TAG = "GalleryResponseConstant"

object GallerySearchConstant {
    const val Total: String = "total"
    const val ObjectIDs: String = "objectIDs"

    val GalleryCollectionApi: (String, Boolean?) -> String = { queryString, _ ->
        "https://collectionapi.metmuseum.org/public/collection/v1/search?q=$queryString"
    }

    const val DepartmentCollectionApi =
        "https://collectionapi.metmuseum.org/public/collection/v1/departments"

    val FailedToFetchDataDefaultMessage =
        Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed to fetch data, please try Again"
}
