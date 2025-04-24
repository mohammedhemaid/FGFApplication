package com.crimsoncodes.apps.fgf_onsite_assignment.di

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import com.crimsoncodes.apps.fgf_onsite_assignment.BuildConfig
import com.crimsoncodes.apps.fgf_onsite_assignment.common.Constants
import com.crimsoncodes.apps.fgf_onsite_assignment.network.ServiceAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Path.Companion.toPath
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideDataApi(
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
        ): ServiceAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ServiceAPI::class.java)
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("x-api-key", "live_tIIEkgvIvK0XdCrt1cMIVAsRMkvenVl4zPlabLHeWbcg52JGCiyyVc8fPQkHak5r")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                }
            )
            .build()

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, percent = 0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache").path.toPath())
                    .maxSizePercent(0.02)
                    .build()
            }
            .components {
                add(OkHttpNetworkFetcherFactory(
                    callFactory = { okhttpCallFactory.get() }
                ))
            }
            .logger(DebugLogger())
            .build()
    }
}