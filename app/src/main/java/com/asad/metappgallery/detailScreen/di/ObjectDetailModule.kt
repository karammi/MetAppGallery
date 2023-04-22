package com.asad.metappgallery.detailScreen.di

import com.asad.metappgallery.detailScreen.data.dataSource.api.ObjectDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ObjectDetailModule {

    @Provides
    fun provideObjectDetailApi(retrofit: Retrofit): ObjectDetailApi =
        retrofit.create(ObjectDetailApi::class.java)
}
