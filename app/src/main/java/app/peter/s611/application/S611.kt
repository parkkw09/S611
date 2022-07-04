package app.peter.s611.application

import app.peter.s611.application.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class S611: DaggerApplication() {

    override fun onCreate() {
        Log.d(TAG, "onCreate()")
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this.applicationContext)
    }

    companion object {
        private const val TAG = "Application"
    }
}