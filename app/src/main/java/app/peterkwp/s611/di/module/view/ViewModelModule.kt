package app.peterkwp.s611.di.module.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.peterkwp.s611.di.annotation.ViewModelKey
import app.peterkwp.s611.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel
}