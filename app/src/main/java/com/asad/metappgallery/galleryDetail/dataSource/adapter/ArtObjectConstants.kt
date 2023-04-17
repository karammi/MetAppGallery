package com.asad.metappgallery.galleryDetail.dataSource.adapter

object ArtObjectConstants {

    val FetchObjectDetailEndpointApi: (Int) -> String = { objectId ->
        "https://collectionapi.metmuseum.org/public/collection/v1/objects/$objectId"
    }
}
