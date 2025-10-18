package org.starter.project.domain.zenn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.starter.project.domain.service.ZennService
import org.starter.project.domain.zenn.service.ZennServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainZennBinds {

    @Singleton
    @Binds
    abstract fun bindZennService(
        zennServiceImpl: ZennServiceImpl
    ): ZennService
}
