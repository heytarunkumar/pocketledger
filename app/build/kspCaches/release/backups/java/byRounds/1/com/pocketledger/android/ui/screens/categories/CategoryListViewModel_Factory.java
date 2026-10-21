package com.pocketledger.android.ui.screens.categories;

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
public final class CategoryListViewModel_Factory implements Factory<CategoryListViewModel> {
  private final Provider<PocketLedgerRepository> repositoryProvider;

  public CategoryListViewModel_Factory(Provider<PocketLedgerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CategoryListViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static CategoryListViewModel_Factory create(
      Provider<PocketLedgerRepository> repositoryProvider) {
    return new CategoryListViewModel_Factory(repositoryProvider);
  }

  public static CategoryListViewModel newInstance(PocketLedgerRepository repository) {
    return new CategoryListViewModel(repository);
  }
}
