package com.example.e_commerce_project.data


import android.content.Context
import android.content.SharedPreferences
import com.example.e_commerce_project.data.repository.NetworkUserRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.data.remote.ApiInterface
import com.example.e_commerce_project.data.repository.StoreRepositoryImpl
import com.example.e_commerce_project.data.repository.UserPreferencesRepositoryImpl
import com.example.e_commerce_project.domain.repository.StoreRepository
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.canerture.com/ecommerce/"
    private const val PREFS_NAME = "user_preferences"
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
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleAbstract {
    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository
    @Binds
    @Singleton
    abstract fun bindSingletonUserRepository(repo: NetworkUserRepository): UserRepository
    @Binds
    @Singleton
    abstract fun bindStoreRepository(
        impl: StoreRepositoryImpl
    ): StoreRepository
}