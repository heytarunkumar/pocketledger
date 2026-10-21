package com.pocketledger.android.di

import android.content.Context
import com.pocketledger.android.data.local.PocketLedgerDatabase
import com.pocketledger.android.data.local.dao.AccountDao
import com.pocketledger.android.data.local.dao.BudgetDao
import com.pocketledger.android.data.local.dao.CategoryDao
import com.pocketledger.android.data.local.dao.RecurringTransactionDao
import com.pocketledger.android.data.local.dao.SavingsGoalDao
import com.pocketledger.android.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): PocketLedgerDatabase {
        return PocketLedgerDatabase.getInstance(context, scope)
    }

    @Provides
    fun provideAccountDao(db: PocketLedgerDatabase): AccountDao = db.accountDao()

    @Provides
    fun provideCategoryDao(db: PocketLedgerDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideTransactionDao(db: PocketLedgerDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideBudgetDao(db: PocketLedgerDatabase): BudgetDao = db.budgetDao()

    @Provides
    fun provideRecurringTransactionDao(db: PocketLedgerDatabase): RecurringTransactionDao = db.recurringTransactionDao()

    @Provides
    fun provideSavingsGoalDao(db: PocketLedgerDatabase): SavingsGoalDao = db.savingsGoalDao()
}
