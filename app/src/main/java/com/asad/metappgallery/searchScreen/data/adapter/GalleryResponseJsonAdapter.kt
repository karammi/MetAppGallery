package com.asad.metappgallery.searchScreen.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse
import org.json.JSONObject

class GalleryResponseJsonAdapter : JsonAdapter<GalleryResponse> {
    override fun createEntityFromJson(json: JSONObject): GalleryResponse {
        val jsonTotal = json.getInt(GalleryResponseConstant.Total)
        // Retrieve the JSONArray associated with the key "objectIDs"
        val jsonArray = if (!json.isNull(GalleryResponseConstant.ObjectIDs)) {
            json.getJSONArray(GalleryResponseConstant.ObjectIDs)
        } else {
            null
        }

        // TODO("move to another function")
        // Convert JSONArray to a list of Int values
        val objectIDsList = jsonArray?.let { array ->
            generateSequence(0) { it + 1 }
                .takeWhile { it < jsonArray.length() }
                .map { array.getInt(it) }
                .toList()
        }

        return GalleryResponse(
            total = jsonTotal,
            objectIDs = objectIDsList,
        )
    }
}
