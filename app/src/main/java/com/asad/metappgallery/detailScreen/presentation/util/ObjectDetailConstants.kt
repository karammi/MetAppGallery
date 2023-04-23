package com.asad.metappgallery.detailScreen.presentation.util

object ObjectDetailConstants {

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

    const val GalleryDataEmptyValue = "Sorry! There isn't more data to show you."

    const val ObjectContentDescriptionTitle = "object_title"
    const val ArtistContentDescriptionTitle = "artist_title"
    const val DepartmentContentDescriptionTitle = "department_title"
    const val ClassificationContentDescriptionTitle = "classification_title"
    const val CircularProgressContentDescription = "circular_progress"
    const val ColumnContentDescription = "column"

    const val AAT_URL = "AAT_URL"
    const val Wikidata_URL = "Wikidata_URL"

    const val ConstituentID = "constituentID"
    const val ConstituentULAN_URL = "constituentULAN_URL"
    const val ConstituentWikidata_URL = "constituentWikidata_URL"
}
