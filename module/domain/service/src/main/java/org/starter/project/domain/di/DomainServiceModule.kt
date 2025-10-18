package org.starter.project.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import org.starter.project.domain.service.ResultHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainServiceModule {

    @Singleton
    @Provides
    fun provideResultHandler(): ResultHandler = ResultHandler(Dispatchers.IO)
}
