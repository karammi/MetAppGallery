package com.asad.metappgallery.detailScreen.data.adapter

import com.asad.metappgallery.core.data.getArrayNullable
import org.json.JSONObject

class AdditionalImagesJsonDeserializer : JsonDeserializer<List<String>> {
    override fun deserialize(json: JSONObject): List<String>? {
        val jsonArray = json.getArrayNullable("additionalImages")
        return jsonArray?.let { currentJsonArray ->
            val additionalImagesList = mutableListOf<String>()
            for (index in 0 until currentJsonArray.length()) {
                additionalImagesList.add(currentJsonArray.getString(index))
            }
            additionalImagesList
        }
    }
}
