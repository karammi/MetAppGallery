package com.asad.metappgallery.searchScreen.data.adapter

object GalleryResponseConstant {
    const val Total: String = "total"
    const val ObjectIDs: String = "objectIDs"

    val GalleryCollectionApi: (String) -> String = { queryString ->
        "https://collectionapi.metmuseum.org/public/collection/v1/search?q=$queryString"
    }
}
