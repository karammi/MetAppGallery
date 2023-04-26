package com.asad.metappgallery.searchScreen.data.repository.mapper

import com.asad.metappgallery.core.data.ResponseMapper
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.DepartmentEntity
import com.asad.metappgallery.searchScreen.domain.model.DepartmentModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DepartmentModelMapper @Inject constructor() :
    ResponseMapper<DepartmentEntity, DepartmentModel> {

    override fun mapToModel(entity: DepartmentEntity): DepartmentModel =
        with(entity) {
            DepartmentModel(
                departmentId,
                displayName,
            )
        }
}
