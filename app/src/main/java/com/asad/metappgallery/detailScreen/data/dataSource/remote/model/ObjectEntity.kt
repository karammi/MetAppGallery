package com.asad.metappgallery.detailScreen.data.dataSource.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ObjectEntity(
    val objectID: Int,
    val isHighlight: Boolean,
    val isPublicDomain: Boolean,
    val primaryImage: String? = null,
    val primaryImageSmall: String? = null,
    val additionalImages: List<String>? = null,
    val constituentEntities: List<ConstituentEntity>? = null,
    val department: String? = null,
    val objectName: String? = null,
    val title: String? = null,
    val culture: String? = null,
    val portfolio: String,
    val artistDisplayName: String,
    val artistDisplayBio: String?,
    val objectDate: String,
    val objectBeginDate: Int,
    val objectEndDate: Int,
    val classification: String,
    val metadataDate: String,
    val repository: String,
    val objectURL: String,
    val tagEntities: List<TagEntity>? = null,
)
