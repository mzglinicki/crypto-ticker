package com.limba.cryptoticker.infrastructure.di

import com.limba.cryptoticker.domain.repository.TokenDataRepository
import com.limba.cryptoticker.infrastructure.repository.TokenDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindTokenDataRepository(impl: TokenDataRepositoryImpl): TokenDataRepository
}