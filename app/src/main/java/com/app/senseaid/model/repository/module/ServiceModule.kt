package com.app.senseaid.model.repository.module

import com.app.senseaid.model.repository.impl.FirestoreRepositoryImpl
import com.app.senseaid.model.repository.impl.StorageRepositoryImpl
import com.app.senseaid.model.repository.FirestoreRepository
import com.app.senseaid.model.repository.StorageRepository
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