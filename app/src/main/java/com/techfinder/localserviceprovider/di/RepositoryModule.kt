package com.techfinder.localserviceprovider.di

import com.techfinder.localserviceprovider.data.repository.AuthRepositoryImp
import com.techfinder.localserviceprovider.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl : AuthRepositoryImp
    ): AuthRepository
}
