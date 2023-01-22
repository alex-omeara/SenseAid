package com.app.senseaid.data.module

import com.app.senseaid.data.repository.FirestoreRepositoryImpl
import com.app.senseaid.data.repository.StorageRepositoryImpl
import com.app.senseaid.domain.repository.FirestoreRepository
import com.app.senseaid.domain.repository.StorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideFirestoreService(impl: FirestoreRepositoryImpl): FirestoreRepository

    @Binds
    abstract fun provideStorageService(impl: StorageRepositoryImpl): StorageRepository
}