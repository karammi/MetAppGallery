package com.asad.metappgallery.detailScreen.dataSource.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.detailScreen.dataSource.model.ConstituentModel
import org.json.JSONObject

class ConstituentJsonAdapter : JsonAdapter<ConstituentModel> {
    override fun createEntityFromJson(json: JSONObject): ConstituentModel {
        return ConstituentModel(
            constituentId = json.getInt(ConstituentConstants.ConstituentID),
            role = json.getString(ConstituentConstants.Role),
            name = json.getString(ConstituentConstants.Name),
            constituentULanURL = json.getString(ConstituentConstants.ConstituentULAN_URL),
            constituentWikidataURL = json.getString(ConstituentConstants.ConstituentWikidata_URL),
            gender = json.getString(ConstituentConstants.Gender),
        )
    }
}
