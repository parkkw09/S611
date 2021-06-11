package app.peterkwp.s611.di.module.component

import app.peterkwp.s611.domain.data.DomainConst
import app.peterkwp.s611.domain.remote.Api
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun provideConverterFactoryJson(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    @Provides
    fun provideApi(okHttpClient: OkHttpClient,
                   converter: Converter.Factory,
                   callAdapter: CallAdapter.Factory): Api
            = Retrofit.Builder()
                      .baseUrl(DomainConst.URL)
                      .client(okHttpClient)
                      .addConverterFactory(converter)
                      .addCallAdapterFactory(callAdapter)
                      .build()
                      .create(Api::class.java)
}