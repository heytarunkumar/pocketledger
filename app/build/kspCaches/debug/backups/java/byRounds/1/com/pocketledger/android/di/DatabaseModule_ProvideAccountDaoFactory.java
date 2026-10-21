package com.pocketledger.android.di;

import com.pocketledger.android.data.local.PocketLedgerDatabase;
import com.pocketledger.android.data.local.dao.AccountDao;
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
public final class DatabaseModule_ProvideAccountDaoFactory implements Factory<AccountDao> {
  private final Provider<PocketLedgerDatabase> dbProvider;

  public DatabaseModule_ProvideAccountDaoFactory(Provider<PocketLedgerDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AccountDao get() {
    return provideAccountDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAccountDaoFactory create(
      Provider<PocketLedgerDatabase> dbProvider) {
    return new DatabaseModule_ProvideAccountDaoFactory(dbProvider);
  }

  public static AccountDao provideAccountDao(PocketLedgerDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAccountDao(db));
  }
}
