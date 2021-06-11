package app.peterkwp.s611.di.module.component

import android.content.Context
import app.peterkwp.s611.util.GlideApp
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides

@Module
class GlideModule {

    @Provides
    fun provideRequestManager(context: Context): RequestManager = GlideApp.with(context)
}