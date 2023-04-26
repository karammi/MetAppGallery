package com.asad.metappgallery.detailScreen.data.dataSource.remote.model

import com.asad.metappgallery.detailScreen.presentation.util.ObjectDetailConstants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConstituentEntity(
    @Json(name = ObjectDetailConstants.ConstituentID)
    val constituentId: Int,
    val role: String,
    val name: String,
    @Json(name = ObjectDetailConstants.ConstituentULAN_URL)
    val constituentULanURL: String,
    @Json(name = ObjectDetailConstants.ConstituentWikidata_URL)
    val constituentWikidataURL: String,
    val gender: String? = null,
)
