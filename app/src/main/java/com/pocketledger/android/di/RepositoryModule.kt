package com.pocketledger.android.di

import com.pocketledger.android.data.repository.PocketLedgerRepository
import com.pocketledger.android.data.repository.PocketLedgerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPocketLedgerRepository(
        impl: PocketLedgerRepositoryImpl
    ): PocketLedgerRepository
}
