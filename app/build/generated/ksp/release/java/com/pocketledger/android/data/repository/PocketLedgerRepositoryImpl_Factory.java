package com.pocketledger.android.data.repository;

import com.pocketledger.android.data.local.dao.AccountDao;
import com.pocketledger.android.data.local.dao.BudgetDao;
import com.pocketledger.android.data.local.dao.CategoryDao;
import com.pocketledger.android.data.local.dao.RecurringTransactionDao;
import com.pocketledger.android.data.local.dao.SavingsGoalDao;
import com.pocketledger.android.data.local.dao.TransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class PocketLedgerRepositoryImpl_Factory implements Factory<PocketLedgerRepositoryImpl> {
  private final Provider<AccountDao> accountDaoProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<RecurringTransactionDao> recurringTransactionDaoProvider;

  private final Provider<SavingsGoalDao> savingsGoalDaoProvider;

  public PocketLedgerRepositoryImpl_Factory(Provider<AccountDao> accountDaoProvider,
      Provider<CategoryDao> categoryDaoProvider, Provider<TransactionDao> transactionDaoProvider,
      Provider<BudgetDao> budgetDaoProvider,
      Provider<RecurringTransactionDao> recurringTransactionDaoProvider,
      Provider<SavingsGoalDao> savingsGoalDaoProvider) {
    this.accountDaoProvider = accountDaoProvider;
    this.categoryDaoProvider = categoryDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.budgetDaoProvider = budgetDaoProvider;
    this.recurringTransactionDaoProvider = recurringTransactionDaoProvider;
    this.savingsGoalDaoProvider = savingsGoalDaoProvider;
  }

  @Override
  public PocketLedgerRepositoryImpl get() {
    return newInstance(accountDaoProvider.get(), categoryDaoProvider.get(), transactionDaoProvider.get(), budgetDaoProvider.get(), recurringTransactionDaoProvider.get(), savingsGoalDaoProvider.get());
  }

  public static PocketLedgerRepositoryImpl_Factory create(Provider<AccountDao> accountDaoProvider,
      Provider<CategoryDao> categoryDaoProvider, Provider<TransactionDao> transactionDaoProvider,
      Provider<BudgetDao> budgetDaoProvider,
      Provider<RecurringTransactionDao> recurringTransactionDaoProvider,
      Provider<SavingsGoalDao> savingsGoalDaoProvider) {
    return new PocketLedgerRepositoryImpl_Factory(accountDaoProvider, categoryDaoProvider, transactionDaoProvider, budgetDaoProvider, recurringTransactionDaoProvider, savingsGoalDaoProvider);
  }

  public static PocketLedgerRepositoryImpl newInstance(AccountDao accountDao,
      CategoryDao categoryDao, TransactionDao transactionDao, BudgetDao budgetDao,
      RecurringTransactionDao recurringTransactionDao, SavingsGoalDao savingsGoalDao) {
    return new PocketLedgerRepositoryImpl(accountDao, categoryDao, transactionDao, budgetDao, recurringTransactionDao, savingsGoalDao);
  }
}
