package com.pocketledger.android.ui.screens.add_transaction;

import androidx.lifecycle.SavedStateHandle;
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
public final class AddTransactionViewModel_Factory implements Factory<AddTransactionViewModel> {
  private final Provider<PocketLedgerRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddTransactionViewModel_Factory(Provider<PocketLedgerRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddTransactionViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static AddTransactionViewModel_Factory create(
      Provider<PocketLedgerRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddTransactionViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static AddTransactionViewModel newInstance(PocketLedgerRepository repository,
      SavedStateHandle savedStateHandle) {
    return new AddTransactionViewModel(repository, savedStateHandle);
  }
}
