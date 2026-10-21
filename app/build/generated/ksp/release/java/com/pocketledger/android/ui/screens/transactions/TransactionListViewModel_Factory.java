package com.pocketledger.android.ui.screens.transactions;

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
public final class TransactionListViewModel_Factory implements Factory<TransactionListViewModel> {
  private final Provider<PocketLedgerRepository> repositoryProvider;

  public TransactionListViewModel_Factory(Provider<PocketLedgerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TransactionListViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static TransactionListViewModel_Factory create(
      Provider<PocketLedgerRepository> repositoryProvider) {
    return new TransactionListViewModel_Factory(repositoryProvider);
  }

  public static TransactionListViewModel newInstance(PocketLedgerRepository repository) {
    return new TransactionListViewModel(repository);
  }
}
