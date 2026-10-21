package com.pocketledger.android.ui.screens.accounts;

import com.pocketledger.android.data.repository.PocketLedgerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AccountListViewModel_Factory implements Factory<AccountListViewModel> {
  private final Provider<PocketLedgerRepository> repositoryProvider;

  public AccountListViewModel_Factory(Provider<PocketLedgerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AccountListViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AccountListViewModel_Factory create(
      Provider<PocketLedgerRepository> repositoryProvider) {
    return new AccountListViewModel_Factory(repositoryProvider);
  }

  public static AccountListViewModel newInstance(PocketLedgerRepository repository) {
    return new AccountListViewModel(repository);
  }
}
