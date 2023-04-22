package com.asad.metappgallery.detailScreen.data.dataSource.remote.model

import com.asad.metappgallery.detailScreen.data.ObjectConstants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConstituentEntity(
    @Json(name = ObjectConstants.ConstituentID)
    val constituentId: Int,
    val role: String,
    val name: String,
    @Json(name = ObjectConstants.ConstituentULAN_URL)
    val constituentULanURL: String,
    @Json(name = ObjectConstants.ConstituentWikidata_URL)
    val constituentWikidataURL: String,
    val gender: String? = null,
)
