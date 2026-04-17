package app.peter.s611.application.di.module.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.peter.s611.application.di.annotation.ViewModelKey
import app.peter.s611.presentation.MainViewModel
import app.peter.s611.presentation.book.NewBookViewModel
import app.peter.s611.presentation.bookmark.BookmarkViewModel
import app.peter.s611.presentation.detail.DetailViewModel
import app.peter.s611.presentation.history.HistoryViewModel
import app.peter.s611.presentation.search.SearchViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(NewBookViewModel::class)
    internal abstract fun newBookViewModel(viewModel: NewBookViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun searchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkViewModel::class)
    internal abstract fun bookmarkViewModel(viewModel: BookmarkViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    internal abstract fun historyViewModel(viewModel: HistoryViewModel): ViewModel
}