package org.starter.project.data.zenn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.starter.project.data.repository.ZennRepository
import org.starter.project.data.zenn.repository.ZennRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataZennBinds {

    @Singleton
    @Binds
    abstract fun bindZennRepository(
        zennRepositoryImpl: ZennRepositoryImpl
    ): ZennRepository
}
