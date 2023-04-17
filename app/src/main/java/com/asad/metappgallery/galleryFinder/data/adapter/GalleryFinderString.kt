package com.asad.metappgallery.galleryFinder.data.adapter

object GalleryFinderString {
    const val Total: String = "total"
    const val ObjectIDs: String = "objectIDs"

    val FetchGalleriesEndPointApi: (String) -> String = { queryString ->
        "https://collectionapi.metmuseum.org/public/collection/v1/search?q=$queryString"
    }
}
