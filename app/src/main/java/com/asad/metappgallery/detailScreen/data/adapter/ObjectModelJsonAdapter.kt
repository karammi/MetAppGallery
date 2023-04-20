package com.asad.metappgallery.detailScreen.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.core.data.getStringNullable
import com.asad.metappgallery.detailScreen.data.model.ObjectModel
import org.json.JSONObject

/**
 * This class is used to deserialize Object Model from JsonObject
 * */
class ObjectModelJsonAdapter constructor(
    private val constituentJsonDeserializer: ConstituentJsonDeserializer,
    private val additionalImagesJsonDeserializer: AdditionalImagesJsonDeserializer,
    private val tagJsonDeserializer: TagJsonDeserializer,
) : JsonAdapter<ObjectModel> {

    override fun createEntityFromJson(json: JSONObject): ObjectModel {
        // Deserialize JSONArray into List<String> of AdditionalImages
        val additionalImagesList = additionalImagesJsonDeserializer.deserialize(json)

        // Deserialize JSONArray into List<Constituent>
        val constituentsList = constituentJsonDeserializer.deserialize(json)

        // Deserialize JSONArray into List<Tag>
        val tagsList = tagJsonDeserializer.deserialize(json)

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
}

