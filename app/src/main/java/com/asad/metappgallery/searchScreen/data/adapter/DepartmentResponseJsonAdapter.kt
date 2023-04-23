package com.asad.metappgallery.searchScreen.data.adapter

import com.asad.metappgallery.core.data.JsonAdapter
import com.asad.metappgallery.core.data.getArrayNullable
import com.asad.metappgallery.searchScreen.data.model.Department
import com.asad.metappgallery.searchScreen.data.model.DepartmentResponse
import org.json.JSONObject

class DepartmentResponseJsonAdapter constructor(
    private val departmentJsonAdapter: DepartmentJsonAdapter,
) : JsonAdapter<DepartmentResponse> {
    override fun createEntityFromJson(json: JSONObject): DepartmentResponse {
        val jsonArray = json.getArrayNullable("departments")
        val departmentList = mutableListOf<Department>()
        jsonArray?.let { array ->
            for (index in 0 until array.length()) {
                departmentList.add(
                    departmentJsonAdapter.createEntityFromJson(
                        array.getJSONObject(index),
                    ),
                )
            }
        }

        return DepartmentResponse(departments = departmentList)
    }
}
