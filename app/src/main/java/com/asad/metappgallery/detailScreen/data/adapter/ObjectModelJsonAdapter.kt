package com.asad.metappgallery.detailScreen.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.core.data.getArrayNullable
import com.asad.metappgallery.core.data.getStringNullable
import com.asad.metappgallery.detailScreen.data.model.ConstituentModel
import com.asad.metappgallery.detailScreen.data.model.ObjectModel
import com.asad.metappgallery.detailScreen.data.model.TagModel
import org.json.JSONArray
import org.json.JSONObject

/**
 * This is used to deserialize Json Object
 * */
class ObjectModelJsonAdapter constructor(
    private val constituentJsonAdapter: ConstituentJsonAdapter,
    private val tagJsonAdapter: TagJsonAdapter,
) : JsonAdapter<ObjectModel> {

    override fun createEntityFromJson(json: JSONObject): ObjectModel {
        // Deserialize JSONArray into List<String> of AdditionalImages
        val additionalImagesList = deserializeAdditionalImages(json.getArrayNullable("additionalImages"))

        // Deserialize JSONArray into List<Constituent>
        val constituentsList = deserializeConstituentList(json.getArrayNullable("constituents"))

        // Deserialize JSONArray into List<Tag>
        val tagsList = deserializeTagList(json.getArrayNullable("tags"))

        return ObjectModel(
            objectID = json.getInt("objectID"),
            isHighlight = json.getBoolean("isHighlight"),
            isPublicDomain = json.getBoolean("isPublicDomain"),
            primaryImage = json.getStringNullable("primaryImage"),
            primaryImageSmall = json.getStringNullable("primaryImageSmall"),
            additionalImages = additionalImagesList,
            department = json.getStringNullable("department"),
            objectName = json.getStringNullable("objectName"),
            title = json.getStringNullable("title"),
            culture = json.getStringNullable("culture"),
            portfolio = json.getString("portfolio"),
            artistDisplayName = json.getString("artistDisplayName"),
            artistDisplayBio = json.getString("artistDisplayBio"),
            objectDate = json.getString("objectDate"),
            objectBeginDate = json.getInt("objectBeginDate"),
            objectEndDate = json.getInt("objectEndDate"),
            constituentModels = constituentsList,
            tagModels = tagsList,
            classification = json.getString("classification"),
            metadataDate = json.getString("metadataDate"),
            repository = json.getString("repository"),
            objectURL = json.getString("objectURL"),
        )
    }

    private fun deserializeConstituentList(jsonArray: JSONArray?): List<ConstituentModel>? {
        return jsonArray?.let {
            val constituentsList = mutableListOf<ConstituentModel>()
            for (index in 0 until jsonArray.length()) {
                constituentsList.add(
                    constituentJsonAdapter.createEntityFromJson(jsonArray.getJSONObject(index)),
                )
            }
            constituentsList
        }
    }

    private fun deserializeTagList(jsonArray: JSONArray?): List<TagModel>? {
        return jsonArray?.let {
            val tagsList = mutableListOf<TagModel>()
            for (index in 0 until jsonArray.length()) {
                tagsList.add(tagJsonAdapter.createEntityFromJson(jsonArray.getJSONObject(index)))
            }
            tagsList
        }
    }

    private fun deserializeAdditionalImages(jsonArray: JSONArray?): List<String>? {
        return jsonArray?.let {
            val additionalImagesList = mutableListOf<String>()
            for (index in 0 until jsonArray.length()) {
                additionalImagesList.add(jsonArray.getString(index))
            }
            additionalImagesList
        }
    }
}
