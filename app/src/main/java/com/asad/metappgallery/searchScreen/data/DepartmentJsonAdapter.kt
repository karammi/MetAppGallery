package com.asad.metappgallery.searchScreen.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.searchScreen.data.model.Department
import org.json.JSONObject

class DepartmentJsonAdapter : JsonAdapter<Department> {
    override fun createEntityFromJson(json: JSONObject): Department {
        return Department(
            departmentId = json.getInt("departmentId"),
            displayName = json.getString("displayName"),
        )
    }
}
