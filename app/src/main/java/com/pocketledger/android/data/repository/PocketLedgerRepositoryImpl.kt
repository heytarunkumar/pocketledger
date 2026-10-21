package com.pocketledger.android.data.repository

import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.data.local.dao.AccountDao
import com.pocketledger.android.data.local.dao.BudgetDao
import com.pocketledger.android.data.local.dao.CategoryDao
import com.pocketledger.android.data.local.dao.RecurringTransactionDao
import com.pocketledger.android.data.local.dao.SavingsGoalDao
import com.pocketledger.android.data.local.dao.TransactionDao
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.local.entity.BudgetEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.RecurringTransactionEntity
import com.pocketledger.android.data.local.entity.SavingsGoalEntity
import com.pocketledger.android.data.local.entity.TransactionEntity
import com.pocketledger.android.data.local.entity.TransactionWithDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PocketLedgerRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val budgetDao: BudgetDao,
    private val recurringTransactionDao: RecurringTransactionDao,
    private val savingsGoalDao: SavingsGoalDao
) : PocketLedgerRepository {

    override fun getAllAccounts(): Flow<List<AccountEntity>> =
        accountDao.getAllAccounts()

    override suspend fun getAccountById(id: Long): AccountEntity? =
        accountDao.getAccountById(id)

    override suspend fun insertAccount(account: AccountEntity): Long =
        accountDao.insertAccount(account)

    override suspend fun updateAccount(account: AccountEntity) =
        accountDao.updateAccount(account)

    override suspend fun deleteAccount(account: AccountEntity) =
        accountDao.deleteAccount(account)

    override fun getAllActiveCategories(): Flow<List<CategoryEntity>> =
        categoryDao.getAllActiveCategories()

    override fun getCategoriesByType(type: CategoryType): Flow<List<CategoryEntity>> =
        categoryDao.getCategoriesByType(type)

    override suspend fun getCategoryById(id: Long): CategoryEntity? =
        categoryDao.getCategoryById(id)

    override suspend fun insertCategory(category: CategoryEntity): Long =
        categoryDao.insertCategory(category)

    override fun getAllTransactionsWithDetails(): Flow<List<TransactionWithDetails>> =
        transactionDao.getAllTransactionsWithDetails()

    override fun getAllTransactions(): Flow<List<TransactionEntity>> =
        transactionDao.getAllTransactions()

    override fun getTransactionsForPeriod(startTime: Long, endTime: Long): Flow<List<TransactionEntity>> =
        transactionDao.getTransactionsForPeriod(startTime, endTime)

    override suspend fun getTransactionWithDetailsById(id: Long): TransactionWithDetails? =
        transactionDao.getTransactionWithDetailsById(id)

    override suspend fun insertTransaction(transaction: TransactionEntity): Long =
        transactionDao.insertTransaction(transaction)

    override suspend fun updateTransaction(transaction: TransactionEntity) =
        transactionDao.updateTransaction(transaction)

    override suspend fun deleteTransaction(transaction: TransactionEntity) =
        transactionDao.deleteTransaction(transaction)

    override fun getBudgetsForMonth(month: Int, year: Int): Flow<List<BudgetEntity>> =
        budgetDao.getBudgetsForMonth(month, year)

    override suspend fun insertBudget(budget: BudgetEntity): Long =
        budgetDao.insertBudget(budget)

    override suspend fun updateBudget(budget: BudgetEntity) =
        budgetDao.updateBudget(budget)

    override suspend fun deleteBudget(budget: BudgetEntity) =
        budgetDao.deleteBudget(budget)

    override fun getAllActiveRecurringTransactions(): Flow<List<RecurringTransactionEntity>> =
        recurringTransactionDao.getAllActiveRecurringTransactions()

    override suspend fun getDueRecurringTransactions(currentTime: Long): List<RecurringTransactionEntity> =
        recurringTransactionDao.getDueRecurringTransactions(currentTime)

    override suspend fun insertRecurringTransaction(entity: RecurringTransactionEntity): Long =
        recurringTransactionDao.insertRecurringTransaction(entity)

    override suspend fun updateRecurringTransaction(entity: RecurringTransactionEntity) =
        recurringTransactionDao.updateRecurringTransaction(entity)

    override suspend fun deleteRecurringTransaction(entity: RecurringTransactionEntity) =
        recurringTransactionDao.deleteRecurringTransaction(entity)

    override fun getAllSavingsGoals(): Flow<List<SavingsGoalEntity>> =
        savingsGoalDao.getAllSavingsGoals()

    override suspend fun insertSavingsGoal(goal: SavingsGoalEntity): Long =
        savingsGoalDao.insertSavingsGoal(goal)

    override suspend fun updateSavingsGoal(goal: SavingsGoalEntity) =
        savingsGoalDao.updateSavingsGoal(goal)

    override suspend fun deleteSavingsGoal(goal: SavingsGoalEntity) =
        savingsGoalDao.deleteSavingsGoal(goal)
}
