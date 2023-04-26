package com.asad.metappgallery.searchScreen.data.repository.mapper

import com.asad.metappgallery.core.data.ResponseMapper
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.DepartmentResponseEntity
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DepartmentResponseModelMapper @Inject constructor(
    private val departmentModelMapper: DepartmentModelMapper,
) :
    ResponseMapper<DepartmentResponseEntity, DepartmentResponseModel> {
    override fun mapToModel(entity: DepartmentResponseEntity): DepartmentResponseModel =
        with(entity) {
            DepartmentResponseModel(
                departmentModels = departmentEntities.map { departmentEntity ->
                    departmentModelMapper.mapToModel(
                        departmentEntity,
                    )
                },
            )
        }
}

