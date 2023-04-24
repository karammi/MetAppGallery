package com.asad.metappgallery.detailScreen.di

import com.asad.metappgallery.detailScreen.data.dataSource.remote.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.data.dataSource.remote.ObjectDetailRemoteDataSourceImpl
import com.asad.metappgallery.detailScreen.data.repository.ObjectDetailRepositoryImpl
import com.asad.metappgallery.detailScreen.domain.repository.ObjectDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ObjectDetailBinderModule {

    @Binds
    abstract fun bindObjectDetailRemoteDataSource(
        objectDetailRemoteDataSource: ObjectDetailRemoteDataSourceImpl,
    ): ObjectDetailRemoteDataSource

    @Binds
    abstract fun bindObjectDetailRepository(objectDetailRepository: ObjectDetailRepositoryImpl): ObjectDetailRepository
}
