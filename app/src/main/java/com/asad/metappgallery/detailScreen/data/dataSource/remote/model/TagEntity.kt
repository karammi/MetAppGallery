package com.asad.metappgallery.detailScreen.data.dataSource.remote.model

import com.asad.metappgallery.detailScreen.data.ObjectConstants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TagEntity(
    val term: String,
    @Json(name = ObjectConstants.AAT_URL)
    val aatUrl: String,
    @Json(name = ObjectConstants.Wikidata_URL)
    val wikidataURL: String,
)
