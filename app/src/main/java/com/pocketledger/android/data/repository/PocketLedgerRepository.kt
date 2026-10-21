package com.pocketledger.android.data.repository

import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.local.entity.BudgetEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.RecurringTransactionEntity
import com.pocketledger.android.data.local.entity.SavingsGoalEntity
import com.pocketledger.android.data.local.entity.TransactionEntity
import com.pocketledger.android.data.local.entity.TransactionWithDetails
import kotlinx.coroutines.flow.Flow

interface PocketLedgerRepository {
    // Accounts
    fun getAllAccounts(): Flow<List<AccountEntity>>
    suspend fun getAccountById(id: Long): AccountEntity?
    suspend fun insertAccount(account: AccountEntity): Long
    suspend fun updateAccount(account: AccountEntity)
    suspend fun deleteAccount(account: AccountEntity)

    // Categories
    fun getAllActiveCategories(): Flow<List<CategoryEntity>>
    fun getCategoriesByType(type: CategoryType): Flow<List<CategoryEntity>>
    suspend fun getCategoryById(id: Long): CategoryEntity?
    suspend fun insertCategory(category: CategoryEntity): Long

    // Transactions
    fun getAllTransactionsWithDetails(): Flow<List<TransactionWithDetails>>
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    fun getTransactionsForPeriod(startTime: Long, endTime: Long): Flow<List<TransactionEntity>>
    suspend fun getTransactionWithDetailsById(id: Long): TransactionWithDetails?
    suspend fun insertTransaction(transaction: TransactionEntity): Long
    suspend fun updateTransaction(transaction: TransactionEntity)
    suspend fun deleteTransaction(transaction: TransactionEntity)

    // Budgets
    fun getBudgetsForMonth(month: Int, year: Int): Flow<List<BudgetEntity>>
    suspend fun insertBudget(budget: BudgetEntity): Long
    suspend fun updateBudget(budget: BudgetEntity)
    suspend fun deleteBudget(budget: BudgetEntity)

    // Recurring Transactions
    fun getAllActiveRecurringTransactions(): Flow<List<RecurringTransactionEntity>>
    suspend fun getDueRecurringTransactions(currentTime: Long): List<RecurringTransactionEntity>
    suspend fun insertRecurringTransaction(entity: RecurringTransactionEntity): Long
    suspend fun updateRecurringTransaction(entity: RecurringTransactionEntity)
    suspend fun deleteRecurringTransaction(entity: RecurringTransactionEntity)

    // Savings Goals
    fun getAllSavingsGoals(): Flow<List<SavingsGoalEntity>>
    suspend fun insertSavingsGoal(goal: SavingsGoalEntity): Long
    suspend fun updateSavingsGoal(goal: SavingsGoalEntity)
    suspend fun deleteSavingsGoal(goal: SavingsGoalEntity)
}
