package com.asad.metappgallery.detailScreen.domain.model

data class ConstituentModel(
    val constituentId: Int,
    val role: String,
    val name: String,
    val constituentULanURL: String,
    val constituentWikidataURL: String,
    val gender: String? = null,
)
