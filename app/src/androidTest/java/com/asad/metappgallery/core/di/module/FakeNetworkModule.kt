package com.asad.metappgallery.core.di.module

import com.asad.metappgallery.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class],
)
object FakeNetworkModule {

    @Provides
    fun provideBaseUrl() = "http://localhost:8080/"

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideMoshiConverter(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        moshiConverterFactory: MoshiConverterFactory,
        baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .callFactory { okHttpClient.get().newCall(it) }
            .addConverterFactory(moshiConverterFactory)
            .build()
    }
}
