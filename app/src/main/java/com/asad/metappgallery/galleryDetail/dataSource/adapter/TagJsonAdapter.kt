package com.asad.metappgallery.galleryDetail.dataSource.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.galleryDetail.dataSource.model.Tag
import org.json.JSONObject

class TagJsonAdapter : JsonAdapter<Tag> {
    override fun createEntityFromJson(json: JSONObject): Tag {
        return Tag(
            term = json.getString(TagConstants.Term),
            AAT_URL = json.getString(TagConstants.AAT_URL),
            Wikidata_URL = json.getString(TagConstants.Wikidata_URL),
        )
    }
}