package app.peterkwp.s611.di

import app.peterkwp.s611.di.annotation.ActivityScope
import app.peterkwp.s611.di.module.component.GlideModule
import app.peterkwp.s611.ui.MainActivity
import app.peterkwp.s611.ui.book.NewBookFragment
import app.peterkwp.s611.ui.bookmark.BookmarkFragment
import app.peterkwp.s611.ui.detail.DetailFragment
import app.peterkwp.s611.ui.history.HistoryFragment
import app.peterkwp.s611.ui.main.ViewPagerFragment
import app.peterkwp.s611.ui.search.SearchFragment
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