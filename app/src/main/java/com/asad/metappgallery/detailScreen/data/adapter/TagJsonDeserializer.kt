package com.asad.metappgallery.detailScreen.data.adapter

import com.asad.metappgallery.core.data.getArrayNullable
import com.asad.metappgallery.detailScreen.data.model.TagModel
import org.json.JSONObject

class TagJsonDeserializer(
    private val tagJsonAdapter: TagJsonAdapter,
) : JsonDeserializer<List<TagModel>> {
    override fun deserialize(json: JSONObject): List<TagModel>? {
        val jsonArray = json.getArrayNullable("tags")
        return jsonArray?.let { currentJsonArray ->
            val tagList = mutableListOf<TagModel>()
            for (index in 0 until currentJsonArray.length()) {
                tagList.add(tagJsonAdapter.createEntityFromJson(currentJsonArray.getJSONObject(index)))
            }
            tagList
        }
    }
}
