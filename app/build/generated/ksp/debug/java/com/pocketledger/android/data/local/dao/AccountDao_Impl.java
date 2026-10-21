package com.pocketledger.android.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pocketledger.android.core.model.AccountType;
import com.pocketledger.android.data.local.entity.AccountEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AccountDao_Impl implements AccountDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AccountEntity> __insertionAdapterOfAccountEntity;

  private final EntityDeletionOrUpdateAdapter<AccountEntity> __deletionAdapterOfAccountEntity;

  private final EntityDeletionOrUpdateAdapter<AccountEntity> __updateAdapterOfAccountEntity;

  public AccountDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAccountEntity = new EntityInsertionAdapter<AccountEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `accounts` (`id`,`name`,`type`,`initialBalancePaise`,`currencyCode`,`isExcludedFromNetWorth`,`iconName`,`colorHex`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AccountEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, __AccountType_enumToString(entity.getType()));
        statement.bindLong(4, entity.getInitialBalancePaise());
        statement.bindString(5, entity.getCurrencyCode());
        final int _tmp = entity.isExcludedFromNetWorth() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindString(7, entity.getIconName());
        statement.bindString(8, entity.getColorHex());
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfAccountEntity = new EntityDeletionOrUpdateAdapter<AccountEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `accounts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AccountEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAccountEntity = new EntityDeletionOrUpdateAdapter<AccountEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `accounts` SET `id` = ?,`name` = ?,`type` = ?,`initialBalancePaise` = ?,`currencyCode` = ?,`isExcludedFromNetWorth` = ?,`iconName` = ?,`colorHex` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AccountEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, __AccountType_enumToString(entity.getType()));
        statement.bindLong(4, entity.getInitialBalancePaise());
        statement.bindString(5, entity.getCurrencyCode());
        final int _tmp = entity.isExcludedFromNetWorth() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindString(7, entity.getIconName());
        statement.bindString(8, entity.getColorHex());
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getId());
      }
    };
  }

  @Override
  public Object insertAccount(final AccountEntity account,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAccountEntity.insertAndReturnId(account);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAccounts(final List<AccountEntity> accounts,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAccountEntity.insert(accounts);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAccount(final AccountEntity account,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAccountEntity.handle(account);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateAccount(final AccountEntity account,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAccountEntity.handle(account);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AccountEntity>> getAllAccounts() {
    final String _sql = "SELECT * FROM accounts ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"accounts"}, new Callable<List<AccountEntity>>() {
      @Override
      @NonNull
      public List<AccountEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfInitialBalancePaise = CursorUtil.getColumnIndexOrThrow(_cursor, "initialBalancePaise");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currencyCode");
          final int _cursorIndexOfIsExcludedFromNetWorth = CursorUtil.getColumnIndexOrThrow(_cursor, "isExcludedFromNetWorth");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AccountEntity> _result = new ArrayList<AccountEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AccountEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final AccountType _tmpType;
            _tmpType = __AccountType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpInitialBalancePaise;
            _tmpInitialBalancePaise = _cursor.getLong(_cursorIndexOfInitialBalancePaise);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final boolean _tmpIsExcludedFromNetWorth;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsExcludedFromNetWorth);
            _tmpIsExcludedFromNetWorth = _tmp != 0;
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AccountEntity(_tmpId,_tmpName,_tmpType,_tmpInitialBalancePaise,_tmpCurrencyCode,_tmpIsExcludedFromNetWorth,_tmpIconName,_tmpColorHex,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAccountById(final long id,
      final Continuation<? super AccountEntity> $completion) {
    final String _sql = "SELECT * FROM accounts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AccountEntity>() {
      @Override
      @Nullable
      public AccountEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfInitialBalancePaise = CursorUtil.getColumnIndexOrThrow(_cursor, "initialBalancePaise");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currencyCode");
          final int _cursorIndexOfIsExcludedFromNetWorth = CursorUtil.getColumnIndexOrThrow(_cursor, "isExcludedFromNetWorth");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final AccountEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final AccountType _tmpType;
            _tmpType = __AccountType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpInitialBalancePaise;
            _tmpInitialBalancePaise = _cursor.getLong(_cursorIndexOfInitialBalancePaise);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final boolean _tmpIsExcludedFromNetWorth;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsExcludedFromNetWorth);
            _tmpIsExcludedFromNetWorth = _tmp != 0;
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new AccountEntity(_tmpId,_tmpName,_tmpType,_tmpInitialBalancePaise,_tmpCurrencyCode,_tmpIsExcludedFromNetWorth,_tmpIconName,_tmpColorHex,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __AccountType_enumToString(@NonNull final AccountType _value) {
    switch (_value) {
      case CASH: return "CASH";
      case BANK: return "BANK";
      case DIGITAL_WALLET: return "DIGITAL_WALLET";
      case CREDIT_CARD: return "CREDIT_CARD";
      case OTHER: return "OTHER";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private AccountType __AccountType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "CASH": return AccountType.CASH;
      case "BANK": return AccountType.BANK;
      case "DIGITAL_WALLET": return AccountType.DIGITAL_WALLET;
      case "CREDIT_CARD": return AccountType.CREDIT_CARD;
      case "OTHER": return AccountType.OTHER;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
