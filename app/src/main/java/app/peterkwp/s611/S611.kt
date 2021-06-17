package app.peterkwp.s611

import app.peterkwp.s611.di.DaggerAppComponent
import app.peterkwp.s611.util.Log
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