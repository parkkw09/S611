package app.peter.s611.application.di.module.component

import app.peter.s611.data.repository.source.local.S611Data
import app.peter.s611.data.repository.source.remote.Api
import app.peter.s611.data.repository.LibraryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideLibraryRepository(api: Api, data: S611Data): LibraryRepository = LibraryRepository(api, data)
}