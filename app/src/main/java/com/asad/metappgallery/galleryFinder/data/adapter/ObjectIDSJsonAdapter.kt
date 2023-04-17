package com.asad.metappgallery.galleryFinder.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.galleryFinder.data.model.ObjectIDResponse
import org.json.JSONObject

class ObjectIDSJsonAdapter : JsonAdapter<ObjectIDResponse> {
    override fun createEntityFromJson(json: JSONObject): ObjectIDResponse {
        val jsonTotal = json.getInt(GalleryFinderString.Total)
        // Retrieve the JSONArray associated with the key "objectIDs"
        val jsonArray = if (!json.isNull(GalleryFinderString.ObjectIDs)) {
            json.getJSONArray(GalleryFinderString.ObjectIDs)
        } else {
            null
        }

        // Convert JSONArray to a list of Int values
        val objectIDsList = jsonArray?.let {
            generateSequence(0) { it + 1 }
                .takeWhile { it < jsonArray.length() }
                .map { jsonArray.getInt(it) }
                .toList()
        }

        return ObjectIDResponse(
            total = jsonTotal,
            objectIDs = objectIDsList,
        )
    }
}
