package com.app.senseaid.data.module

import com.app.senseaid.data.repository.FirestoreRepositoryImpl
import com.app.senseaid.domain.repository.FirestoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideLocationsService(impl: FirestoreRepositoryImpl): FirestoreRepository
}