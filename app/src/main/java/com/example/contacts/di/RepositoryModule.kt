package com.example.contacts.di

import com.example.contacts.data.ExternDataSource
import com.example.contacts.data.InternDataSource
import com.example.contacts.repository.ContactRepository
import com.example.contacts.repository.DefaultContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideInternDataSource(): InternDataSource = InternDataSource()

    @Provides
    @Singleton
    fun provideExternDataSource(): ExternDataSource = ExternDataSource()

    @Provides
    @Singleton
    fun provideContactRepository(
        internDataSource: InternDataSource,
        externDataSource: ExternDataSource
    ) : ContactRepository = DefaultContactRepository(internDataSource, externDataSource)
}
