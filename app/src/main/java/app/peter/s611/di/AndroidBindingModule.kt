package app.peter.s611.di

import app.peter.s611.di.annotation.ActivityScope
import app.peter.s611.di.module.component.GlideModule
import app.peter.s611.ui.MainActivity
import app.peter.s611.ui.book.NewBookFragment
import app.peter.s611.ui.bookmark.BookmarkFragment
import app.peter.s611.ui.detail.DetailFragment
import app.peter.s611.ui.history.HistoryFragment
import app.peter.s611.ui.main.ViewPagerFragment
import app.peter.s611.ui.search.SearchFragment
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