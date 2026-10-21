package com.pocketledger.android.ui.screens.settings;

import com.pocketledger.android.core.security.SecurityPreferences;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SecurityPreferences> securityPreferencesProvider;

  private final Provider<PocketLedgerRepository> repositoryProvider;

  public SettingsViewModel_Factory(Provider<SecurityPreferences> securityPreferencesProvider,
      Provider<PocketLedgerRepository> repositoryProvider) {
    this.securityPreferencesProvider = securityPreferencesProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(securityPreferencesProvider.get(), repositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SecurityPreferences> securityPreferencesProvider,
      Provider<PocketLedgerRepository> repositoryProvider) {
    return new SettingsViewModel_Factory(securityPreferencesProvider, repositoryProvider);
  }

  public static SettingsViewModel newInstance(SecurityPreferences securityPreferences,
      PocketLedgerRepository repository) {
    return new SettingsViewModel(securityPreferences, repository);
  }
}
