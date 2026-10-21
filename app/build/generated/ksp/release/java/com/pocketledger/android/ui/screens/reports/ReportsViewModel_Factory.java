package com.pocketledger.android.ui.screens.reports;

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
public final class ReportsViewModel_Factory implements Factory<ReportsViewModel> {
  private final Provider<PocketLedgerRepository> repositoryProvider;

  public ReportsViewModel_Factory(Provider<PocketLedgerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ReportsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ReportsViewModel_Factory create(
      Provider<PocketLedgerRepository> repositoryProvider) {
    return new ReportsViewModel_Factory(repositoryProvider);
  }

  public static ReportsViewModel newInstance(PocketLedgerRepository repository) {
    return new ReportsViewModel(repository);
  }
}
