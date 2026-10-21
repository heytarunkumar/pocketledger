package com.pocketledger.android.di;

import com.pocketledger.android.data.local.PocketLedgerDatabase;
import com.pocketledger.android.data.local.dao.BudgetDao;
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
public final class DatabaseModule_ProvideBudgetDaoFactory implements Factory<BudgetDao> {
  private final Provider<PocketLedgerDatabase> dbProvider;

  public DatabaseModule_ProvideBudgetDaoFactory(Provider<PocketLedgerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BudgetDao get() {
    return provideBudgetDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBudgetDaoFactory create(
      Provider<PocketLedgerDatabase> dbProvider) {
    return new DatabaseModule_ProvideBudgetDaoFactory(dbProvider);
  }

  public static BudgetDao provideBudgetDao(PocketLedgerDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBudgetDao(db));
  }
}
