package com.asad.metappgallery.galleryDetail.dataSource.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.galleryDetail.dataSource.model.Constituent
import org.json.JSONObject

class ConstituentJsonAdapter : JsonAdapter<Constituent> {
    override fun createEntityFromJson(json: JSONObject): Constituent {
        return Constituent(
            constituentID = json.getInt(ConstituentConstants.ConstituentID),
            role = json.getString(ConstituentConstants.Role),
            name = json.getString(ConstituentConstants.Name),
            constituentULAN_URL = json.getString(ConstituentConstants.ConstituentULAN_URL),
            constituentWikidata_URL = json.getString(ConstituentConstants.ConstituentWikidata_URL),
            gender = json.getString(ConstituentConstants.Gender),
        )
    }
}

// "role": "Artist",
// "gender": "Female"
