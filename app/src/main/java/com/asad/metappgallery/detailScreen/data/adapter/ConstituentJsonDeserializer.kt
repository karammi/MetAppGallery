package com.asad.metappgallery.detailScreen.data.adapter

import com.asad.metappgallery.core.data.getArrayNullable
import com.asad.metappgallery.detailScreen.data.model.ConstituentModel
import org.json.JSONObject

class ConstituentJsonDeserializer(
    private val constituentJsonAdapter: ConstituentJsonAdapter,
) : JsonDeserializer<List<ConstituentModel>> {
    override fun deserialize(json: JSONObject): List<ConstituentModel>? {
        val jsonArray = json.getArrayNullable("constituents")
        return jsonArray?.let { currentJsonArray ->
            val constituentsList = mutableListOf<ConstituentModel>()
            for (index in 0 until currentJsonArray.length()) {
                constituentsList.add(
                    constituentJsonAdapter.createEntityFromJson(currentJsonArray.getJSONObject(index)),
                )
            }
            constituentsList
        }
    }
}
