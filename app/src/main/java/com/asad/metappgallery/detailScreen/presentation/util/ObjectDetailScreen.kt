package com.asad.metappgallery.detailScreen.presentation.util

object ObjectDetailScreen {

    val artistDisplayName: (String?) -> String = {
        "Artist display name: ${it ?: "-"}"
    }

    val department: (String?) -> String = {
        "Department: ${it ?: "-"}"
    }

    val culture: (String?) -> String = {
        "Culture: ${it ?: "-"}"
    }

    val classification: (String?) -> String = {
        "Classification: ${it ?: "-"}"
    }

    val portfolio: (String?) -> String = {
        "Portfolio: ${it ?: "-"}"
    }

    val objectDate: (String?) -> String = {
        "ObjectDate: ${it ?: "-"}"
    }

    const val ArtistDisplayBio: String = "Artist Display Bio :"
    const val GalleryDetail: String = "Gallery detail"
    const val GalleryInfo: String = "Gallery Info"
    const val GalleryErrorTitle: String = "Oops! An error occurred!"
}
