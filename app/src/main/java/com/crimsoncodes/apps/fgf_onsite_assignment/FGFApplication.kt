package com.crimsoncodes.apps.fgf_onsite_assignment

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FGFApplication : Application(), SingletonImageLoader.Factory  {

    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return imageLoader.get()
    }
}
