package app.peter.s611.application.di.module.component

import android.content.Context
import app.peter.s611.application.GlideApp
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides

@Module
class GlideModule {

    @Provides
    fun provideRequestManager(context: Context): RequestManager = GlideApp.with(context)
}