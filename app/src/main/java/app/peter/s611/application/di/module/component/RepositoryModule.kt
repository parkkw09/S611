package app.peter.s611.application.di.module.component

import app.peter.s611.data.repository.LibraryRepositoryImpl
import app.peter.s611.domain.repository.LibraryRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLibraryRepository(impl: LibraryRepositoryImpl): LibraryRepository
}