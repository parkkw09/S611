package app.peter.s611.di

import android.content.Context
import app.peter.s611.S611
import app.peter.s611.di.module.component.*
import app.peter.s611.di.module.view.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    AndroidBindingModule::class,
    AndroidSupportInjectionModule::class
])
interface AppComponent: AndroidInjector<S611> {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}