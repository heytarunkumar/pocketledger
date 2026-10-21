package com.pocketledger.android.ui.screens.budgets;

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
public final class BudgetListViewModel_Factory implements Factory<BudgetListViewModel> {
  private final Provider<PocketLedgerRepository> repositoryProvider;

  public BudgetListViewModel_Factory(Provider<PocketLedgerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public BudgetListViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static BudgetListViewModel_Factory create(
      Provider<PocketLedgerRepository> repositoryProvider) {
    return new BudgetListViewModel_Factory(repositoryProvider);
  }

  public static BudgetListViewModel newInstance(PocketLedgerRepository repository) {
    return new BudgetListViewModel(repository);
  }
}
