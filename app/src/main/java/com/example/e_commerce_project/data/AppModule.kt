package com.example.e_commerce_project.data

import com.example.e_commerce_project.util.api.ApiInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.canerture.com/ecommerce/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
    /*
    @Provides
    @Singleton
    fun provideUserRepository(apiInterface: ApiInterface): UserRepository {
        return NetworkUserRepository(apiInterface)
    }

     */
}
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleAbstract {
    @Binds
    @Singleton
    abstract fun bindSingletonUserRepository(repo: NetworkUserRepository): UserRepository
}