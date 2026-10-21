package com.pocketledger.android.di;

import com.pocketledger.android.data.local.PocketLedgerDatabase;
import com.pocketledger.android.data.local.dao.RecurringTransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideRecurringTransactionDaoFactory implements Factory<RecurringTransactionDao> {
  private final Provider<PocketLedgerDatabase> dbProvider;

  public DatabaseModule_ProvideRecurringTransactionDaoFactory(
      Provider<PocketLedgerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public RecurringTransactionDao get() {
    return provideRecurringTransactionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideRecurringTransactionDaoFactory create(
      Provider<PocketLedgerDatabase> dbProvider) {
    return new DatabaseModule_ProvideRecurringTransactionDaoFactory(dbProvider);
  }

  public static RecurringTransactionDao provideRecurringTransactionDao(PocketLedgerDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRecurringTransactionDao(db));
  }
}
