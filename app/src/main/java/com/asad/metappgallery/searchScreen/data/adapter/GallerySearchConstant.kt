package com.asad.metappgallery.searchScreen.data.adapter

import android.util.Log
import com.asad.metappgallery.core.util.Emoji

private const val TAG = "GalleryResponseConstant"

object GallerySearchConstant {
    const val Total: String = "total"
    const val ObjectIDs: String = "objectIDs"

    val GalleryCollectionApi: (String, Boolean?) -> String = { queryString, isHighlight ->
        val temp =
            "https://collectionapi.metmuseum.org/public/collection/v1/search?${isHighlight.let { "isHighlight=$it&" }}q=$queryString"

        Log.d(TAG, "url: $temp")
        temp
    }

    const val DepartmentCollectionApi =
        "https://collectionapi.metmuseum.org/public/collection/v1/departments"

    val FailedToFetchDataDefaultMessage =
        Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed to fetch data, please try Again"
}
