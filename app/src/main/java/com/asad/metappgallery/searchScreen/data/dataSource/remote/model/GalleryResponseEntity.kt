package com.asad.metappgallery.searchScreen.data.dataSource.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryResponseEntity(
    val total: Int,
    val objectIDs: List<Int>?,
)
