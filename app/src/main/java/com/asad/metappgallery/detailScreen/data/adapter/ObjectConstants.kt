package com.asad.metappgallery.detailScreen.data.adapter

object ObjectConstants {

    val FetchObjectDetailEndpointApi: (Int) -> String = { objectId ->
        "https://collectionapi.metmuseum.org/public/collection/v1/objects/$objectId"
    }
}
