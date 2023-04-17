package com.asad.metappgallery.galleryDetail.dataSource.model

data class Constituent(
    val constituentID: Int,
    val role: String,
    val name: String,
    val constituentULAN_URL: String,
    val constituentWikidata_URL: String,
    val gender: String? = null,
)
