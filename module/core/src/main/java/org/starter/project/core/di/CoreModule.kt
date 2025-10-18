package org.starter.project.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.starter.project.core.api.ZennApiClient
import org.starter.project.core.api.ZennApiClientImpl
import org.starter.project.core.datastore.ZennDataStore
import org.starter.project.core.datastore.ZennDataStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideZennApiClient(
        zennApiClientImpl: ZennApiClientImpl
    ): ZennApiClient = zennApiClientImpl

    @Singleton
    @Provides
    fun provideZennDataStore(
        zennDataStoreImpl: ZennDataStoreImpl
    ): ZennDataStore = zennDataStoreImpl
}
