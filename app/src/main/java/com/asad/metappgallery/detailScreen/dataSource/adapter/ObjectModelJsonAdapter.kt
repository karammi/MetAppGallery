package com.asad.metappgallery.detailScreen.dataSource.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.detailScreen.dataSource.model.ObjectModel
import com.asad.metappgallery.detailScreen.dataSource.model.ConstituentModel
import com.asad.metappgallery.detailScreen.dataSource.model.TagModel
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
        val additionalImagesList =
            deserializeAdditionalImages(json.getJSONArray("additionalImages"))

        // Deserialize JSONArray into List<Constituent>
        val constituentsList = deserializeConstituentList(json.getJSONArray("constituents"))

        // Deserialize JSONArray into List<Tag>
        val tagsList = deserializeTagList(json.getJSONArray("tags"))

        return ObjectModel(
            objectID = json.getInt("objectID"),
            isHighlight = json.getBoolean("isHighlight"),
            isPublicDomain = json.getBoolean("isPublicDomain"),
            primaryImage = json.getString("primaryImage"),
            primaryImageSmall = json.getString("primaryImageSmall"),
            additionalImages = additionalImagesList,
            department = json.getString("department"),
            objectName = json.getString("objectName"),
            title = json.getString("title"),
            culture = json.getString("culture"),
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
