package com.asad.metappgallery.detailScreen.data.adapter

import org.json.JSONObject

interface JsonDeserializer<out T> {
    fun deserialize(json: JSONObject): T?
}
