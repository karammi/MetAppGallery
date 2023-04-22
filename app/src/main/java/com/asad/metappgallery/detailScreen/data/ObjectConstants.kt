package com.asad.metappgallery.detailScreen.data

object ObjectConstants {

    val FetchObjectDetailEndpointApi: (Int) -> String = { objectId ->
        "https://collectionapi.metmuseum.org/public/collection/v1/objects/$objectId"
    }

    const val AAT_URL = "AAT_URL"
    const val Wikidata_URL = "Wikidata_URL"

    const val ConstituentID = "constituentID"
    const val ConstituentULAN_URL = "constituentULAN_URL"
    const val ConstituentWikidata_URL = "constituentWikidata_URL"
}
