package com.asad.metappgallery.searchScreen.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.searchScreen.data.model.MetSearchResponse
import org.json.JSONObject

class MetSearchResponseJsonAdapter : JsonAdapter<MetSearchResponse> {
    override fun createEntityFromJson(json: JSONObject): MetSearchResponse {
        val jsonTotal = json.getInt(MetSearchString.Total)
        // Retrieve the JSONArray associated with the key "objectIDs"
        val jsonArray = if (!json.isNull(MetSearchString.ObjectIDs)) {
            json.getJSONArray(MetSearchString.ObjectIDs)
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

        return MetSearchResponse(
            total = jsonTotal,
            objectIDs = objectIDsList,
        )
    }
}
