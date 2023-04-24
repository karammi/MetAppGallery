package com.asad.metappgallery.searchScreen.di

import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.data.repository.GalleryRepositoryImpl
import com.asad.metappgallery.searchScreen.domain.repository.GalleryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GallerySearchBinderModule {
    @Binds
    abstract fun bindGalleryRemoteDataSource(
        galleryRemoteDataSource: GalleryRemoteDataSourceImpl,
    ): GalleryRemoteDataSource

    @Binds
    abstract fun bindGalleryRepository(galleryRepository: GalleryRepositoryImpl): GalleryRepository
}
