package org.starter.project.data.zenn.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.starter.project.core.api.ZennApiClient
import org.starter.project.data.zenn.datasource.api.ZennApi
import org.starter.project.data.zenn.datasource.preferences.ZennPreferences
import org.starter.project.data.zenn.datasource.preferences.ZennPreferencesImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataZennModule {

    @Singleton
    @Provides
    fun provideZennApi(zennApiClient: ZennApiClient): ZennApi =
        zennApiClient.retrofit.create(ZennApi::class.java)

    @Singleton
    @Provides
    fun provideZennPreferences(
        zennPreferencesImpl: ZennPreferencesImpl
    ): ZennPreferences = zennPreferencesImpl
}
