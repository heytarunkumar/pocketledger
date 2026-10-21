package com.pocketledger.android.di;

import com.pocketledger.android.data.local.PocketLedgerDatabase;
import com.pocketledger.android.data.local.dao.SavingsGoalDao;
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
public final class DatabaseModule_ProvideSavingsGoalDaoFactory implements Factory<SavingsGoalDao> {
  private final Provider<PocketLedgerDatabase> dbProvider;

  public DatabaseModule_ProvideSavingsGoalDaoFactory(Provider<PocketLedgerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SavingsGoalDao get() {
    return provideSavingsGoalDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSavingsGoalDaoFactory create(
      Provider<PocketLedgerDatabase> dbProvider) {
    return new DatabaseModule_ProvideSavingsGoalDaoFactory(dbProvider);
  }

  public static SavingsGoalDao provideSavingsGoalDao(PocketLedgerDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSavingsGoalDao(db));
  }
}
