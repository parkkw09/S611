package app.peter.s611.di.module.component

import app.peter.s611.domain.local.S611Data
import app.peter.s611.domain.remote.Api
import app.peter.s611.domain.repository.LibraryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideLibraryRepository(api: Api, data: S611Data): LibraryRepository = LibraryRepository(api, data)
}