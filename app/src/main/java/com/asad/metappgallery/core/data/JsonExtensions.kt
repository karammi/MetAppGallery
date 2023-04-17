package com.asad.metappgallery.core.data

import org.json.JSONArray
import org.json.JSONObject

/**
 * This is an extension of [JSONObject] used when we are not sure
 * if the [JSONObject] contains the key-value pair of [String] type that we want.
 * If it hasn't, it will return null. Otherwise, the value will be returned.
 * */
fun JSONObject.getStringNullable(name: String): String? {
    if (!this.has(name)) return null

    if (this.isNull(name)) return null

    if (this.getString(name) == "null") return null

    return this.getString(name)
}

fun JSONObject.getArrayNullable(name: String): JSONArray? {
    if (!this.has(name)) return null

    if (this.isNull(name)) return null

    if (this.getString(name) == "null") return null

    return this.getJSONArray(name)
}
