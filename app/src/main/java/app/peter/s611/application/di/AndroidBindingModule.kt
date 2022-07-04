package app.peter.s611.application.di

import app.peter.s611.application.di.annotation.ActivityScope
import app.peter.s611.application.di.module.component.GlideModule
import app.peter.s611.presentation.MainActivity
import app.peter.s611.presentation.book.NewBookFragment
import app.peter.s611.presentation.bookmark.BookmarkFragment
import app.peter.s611.presentation.detail.DetailFragment
import app.peter.s611.presentation.history.HistoryFragment
import app.peter.s611.presentation.main.ViewPagerFragment
import app.peter.s611.presentation.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AndroidBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindViewPagerFragment(): ViewPagerFragment

    @ContributesAndroidInjector(modules = [GlideModule::class])
    internal abstract fun bindNewBookFragment(): NewBookFragment

    @ContributesAndroidInjector(modules = [GlideModule::class])
    internal abstract fun bindBookmarkFragment(): BookmarkFragment

    @ContributesAndroidInjector
    internal abstract fun bindHistoryFragment(): HistoryFragment

    @ContributesAndroidInjector(modules = [GlideModule::class])
    internal abstract fun bindDetailFragment(): DetailFragment

    @ContributesAndroidInjector(modules = [GlideModule::class])
    internal abstract fun bindSearchFragment(): SearchFragment
}