package com.asad.metappgallery.searchScreen.di

import com.asad.metappgallery.searchScreen.data.dataSource.api.GalleryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object GallerySearchModule {

    @Provides
    fun provideGalleryApi(retrofit: Retrofit): GalleryApi = retrofit.create(GalleryApi::class.java)
}
