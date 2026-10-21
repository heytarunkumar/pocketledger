package com.pocketledger.android.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pocketledger.android.core.model.AccountType
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        BudgetEntity::class,
        RecurringTransactionEntity::class,
        SavingsGoalEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class PocketLedgerDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun recurringTransactionDao(): RecurringTransactionDao
    abstract fun savingsGoalDao(): SavingsGoalDao

    companion object {
        @Volatile
        private var INSTANCE: PocketLedgerDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): PocketLedgerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PocketLedgerDatabase::class.java,
                    "pocketledger_database"
                )
                .addCallback(DatabaseCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    seedDatabase(database.accountDao(), database.categoryDao())
                }
            }
        }

        private suspend fun seedDatabase(accountDao: AccountDao, categoryDao: CategoryDao) {
            // Default Accounts
            val defaultAccounts = listOf(
                AccountEntity(name = "Cash", type = AccountType.CASH, iconName = "payments", colorHex = "#4CAF50"),
                AccountEntity(name = "Bank Account", type = AccountType.BANK, iconName = "account_balance", colorHex = "#1E88E5"),
                AccountEntity(name = "Credit Card", type = AccountType.CREDIT_CARD, iconName = "credit_card", colorHex = "#E53935")
            )
            accountDao.insertAccounts(defaultAccounts)

            // Default Categories
            val defaultCategories = listOf(
                // Income
                CategoryEntity(name = "Salary", type = CategoryType.INCOME, iconName = "work", colorHex = "#2E7D32", isDefault = true),
                CategoryEntity(name = "Business & Freelance", type = CategoryType.INCOME, iconName = "storefront", colorHex = "#388E3C", isDefault = true),
                CategoryEntity(name = "Investments & Dividends", type = CategoryType.INCOME, iconName = "trending_up", colorHex = "#43A047", isDefault = true),
                CategoryEntity(name = "Gifts & Grants", type = CategoryType.INCOME, iconName = "card_giftcard", colorHex = "#66BB6A", isDefault = true),
                CategoryEntity(name = "Other Income", type = CategoryType.INCOME, iconName = "attach_money", colorHex = "#81C784", isDefault = true),

                // Expense
                CategoryEntity(name = "Housing & Rent", type = CategoryType.EXPENSE, iconName = "home", colorHex = "#C62828", isDefault = true),
                CategoryEntity(name = "Utilities & Bills", type = CategoryType.EXPENSE, iconName = "receipt_long", colorHex = "#D32F2F", isDefault = true),
                CategoryEntity(name = "Groceries & Food", type = CategoryType.EXPENSE, iconName = "shopping_cart", colorHex = "#E53935", isDefault = true),
                CategoryEntity(name = "Dining & Food Delivery", type = CategoryType.EXPENSE, iconName = "restaurant", colorHex = "#F44336", isDefault = true),
                CategoryEntity(name = "Transport & Fuel", type = CategoryType.EXPENSE, iconName = "directions_car", colorHex = "#EF5350", isDefault = true),
                CategoryEntity(name = "Shopping & Household", type = CategoryType.EXPENSE, iconName = "shopping_bag", colorHex = "#E91E63", isDefault = true),
                CategoryEntity(name = "Healthcare & Medical", type = CategoryType.EXPENSE, iconName = "local_hospital", colorHex = "#D81B60", isDefault = true),
                CategoryEntity(name = "Education & Learning", type = CategoryType.EXPENSE, iconName = "school", colorHex = "#8E24AA", isDefault = true),
                CategoryEntity(name = "Subscriptions & Software", type = CategoryType.EXPENSE, iconName = "subscriptions", colorHex = "#5E35B1", isDefault = true),
                CategoryEntity(name = "Entertainment & Leisure", type = CategoryType.EXPENSE, iconName = "movie", colorHex = "#3949AB", isDefault = true),
                CategoryEntity(name = "Travel & Vacations", type = CategoryType.EXPENSE, iconName = "flight", colorHex = "#1E88E5", isDefault = true),
                CategoryEntity(name = "Personal Care", type = CategoryType.EXPENSE, iconName = "spa", colorHex = "#00ACC1", isDefault = true),
                CategoryEntity(name = "Investments & Savings", type = CategoryType.EXPENSE, iconName = "savings", colorHex = "#00897B", isDefault = true),
                CategoryEntity(name = "Taxes & Fees", type = CategoryType.EXPENSE, iconName = "account_balance", colorHex = "#757575", isDefault = true),
                CategoryEntity(name = "Gifts & Donations", type = CategoryType.EXPENSE, iconName = "volunteer_activism", colorHex = "#FB8C00", isDefault = true),
                CategoryEntity(name = "Miscellaneous", type = CategoryType.EXPENSE, iconName = "more_horiz", colorHex = "#78909C", isDefault = true)
            )
            categoryDao.insertCategories(defaultCategories)
        }
    }
}
