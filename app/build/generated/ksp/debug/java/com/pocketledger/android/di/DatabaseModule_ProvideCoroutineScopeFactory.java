package com.pocketledger.android.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideCoroutineScopeFactory implements Factory<CoroutineScope> {
  @Override
  public CoroutineScope get() {
    return provideCoroutineScope();
  }

  public static DatabaseModule_ProvideCoroutineScopeFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineScope provideCoroutineScope() {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCoroutineScope());
  }

  private static final class InstanceHolder {
    private static final DatabaseModule_ProvideCoroutineScopeFactory INSTANCE = new DatabaseModule_ProvideCoroutineScopeFactory();
  }
}
