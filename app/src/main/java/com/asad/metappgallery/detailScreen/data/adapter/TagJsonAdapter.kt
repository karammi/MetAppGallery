package com.asad.metappgallery.detailScreen.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.detailScreen.data.model.TagModel
import org.json.JSONObject

class TagJsonAdapter : JsonAdapter<TagModel> {
    override fun createEntityFromJson(json: JSONObject): TagModel {
        return TagModel(
            term = json.getString(TagConstants.Term),
            aatUrl = json.getString(TagConstants.AAT_URL),
            wikidataURL = json.getString(TagConstants.Wikidata_URL),
        )
    }
}