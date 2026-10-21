package com.pocketledger.android.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pocketledger.android.core.model.PaymentMethod;
import com.pocketledger.android.core.model.RecurrenceFrequency;
import com.pocketledger.android.core.model.TransactionType;
import com.pocketledger.android.data.local.entity.RecurringTransactionEntity;
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
public final class RecurringTransactionDao_Impl implements RecurringTransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecurringTransactionEntity> __insertionAdapterOfRecurringTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<RecurringTransactionEntity> __deletionAdapterOfRecurringTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<RecurringTransactionEntity> __updateAdapterOfRecurringTransactionEntity;

  public RecurringTransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecurringTransactionEntity = new EntityInsertionAdapter<RecurringTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recurring_transactions` (`id`,`type`,`amountPaise`,`note`,`accountId`,`transferDestinationAccountId`,`categoryId`,`paymentMethod`,`frequency`,`startDate`,`nextDueDate`,`isActive`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecurringTransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __TransactionType_enumToString(entity.getType()));
        statement.bindLong(3, entity.getAmountPaise());
        statement.bindString(4, entity.getNote());
        statement.bindLong(5, entity.getAccountId());
        if (entity.getTransferDestinationAccountId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getTransferDestinationAccountId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCategoryId());
        }
        statement.bindString(8, __PaymentMethod_enumToString(entity.getPaymentMethod()));
        statement.bindString(9, __RecurrenceFrequency_enumToString(entity.getFrequency()));
        statement.bindLong(10, entity.getStartDate());
        statement.bindLong(11, entity.getNextDueDate());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfRecurringTransactionEntity = new EntityDeletionOrUpdateAdapter<RecurringTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recurring_transactions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecurringTransactionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRecurringTransactionEntity = new EntityDeletionOrUpdateAdapter<RecurringTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recurring_transactions` SET `id` = ?,`type` = ?,`amountPaise` = ?,`note` = ?,`accountId` = ?,`transferDestinationAccountId` = ?,`categoryId` = ?,`paymentMethod` = ?,`frequency` = ?,`startDate` = ?,`nextDueDate` = ?,`isActive` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecurringTransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __TransactionType_enumToString(entity.getType()));
        statement.bindLong(3, entity.getAmountPaise());
        statement.bindString(4, entity.getNote());
        statement.bindLong(5, entity.getAccountId());
        if (entity.getTransferDestinationAccountId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getTransferDestinationAccountId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCategoryId());
        }
        statement.bindString(8, __PaymentMethod_enumToString(entity.getPaymentMethod()));
        statement.bindString(9, __RecurrenceFrequency_enumToString(entity.getFrequency()));
        statement.bindLong(10, entity.getStartDate());
        statement.bindLong(11, entity.getNextDueDate());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getId());
      }
    };
  }

  @Override
  public Object insertRecurringTransaction(final RecurringTransactionEntity entity,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecurringTransactionEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRecurringTransaction(final RecurringTransactionEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecurringTransactionEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRecurringTransaction(final RecurringTransactionEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRecurringTransactionEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecurringTransactionEntity>> getAllActiveRecurringTransactions() {
    final String _sql = "SELECT * FROM recurring_transactions WHERE isActive = 1 ORDER BY nextDueDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recurring_transactions"}, new Callable<List<RecurringTransactionEntity>>() {
      @Override
      @NonNull
      public List<RecurringTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfNextDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextDueDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<RecurringTransactionEntity> _result = new ArrayList<RecurringTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecurringTransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final TransactionType _tmpType;
            _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpAmountPaise;
            _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final Long _tmpTransferDestinationAccountId;
            if (_cursor.isNull(_cursorIndexOfTransferDestinationAccountId)) {
              _tmpTransferDestinationAccountId = null;
            } else {
              _tmpTransferDestinationAccountId = _cursor.getLong(_cursorIndexOfTransferDestinationAccountId);
            }
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final PaymentMethod _tmpPaymentMethod;
            _tmpPaymentMethod = __PaymentMethod_stringToEnum(_cursor.getString(_cursorIndexOfPaymentMethod));
            final RecurrenceFrequency _tmpFrequency;
            _tmpFrequency = __RecurrenceFrequency_stringToEnum(_cursor.getString(_cursorIndexOfFrequency));
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpNextDueDate;
            _tmpNextDueDate = _cursor.getLong(_cursorIndexOfNextDueDate);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new RecurringTransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpFrequency,_tmpStartDate,_tmpNextDueDate,_tmpIsActive,_tmpCreatedAt);
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
  public Object getDueRecurringTransactions(final long currentTime,
      final Continuation<? super List<RecurringTransactionEntity>> $completion) {
    final String _sql = "SELECT * FROM recurring_transactions WHERE isActive = 1 AND nextDueDate <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RecurringTransactionEntity>>() {
      @Override
      @NonNull
      public List<RecurringTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfNextDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "nextDueDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<RecurringTransactionEntity> _result = new ArrayList<RecurringTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecurringTransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final TransactionType _tmpType;
            _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpAmountPaise;
            _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final long _tmpAccountId;
            _tmpAccountId = _cursor.getLong(_cursorIndexOfAccountId);
            final Long _tmpTransferDestinationAccountId;
            if (_cursor.isNull(_cursorIndexOfTransferDestinationAccountId)) {
              _tmpTransferDestinationAccountId = null;
            } else {
              _tmpTransferDestinationAccountId = _cursor.getLong(_cursorIndexOfTransferDestinationAccountId);
            }
            final Long _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            }
            final PaymentMethod _tmpPaymentMethod;
            _tmpPaymentMethod = __PaymentMethod_stringToEnum(_cursor.getString(_cursorIndexOfPaymentMethod));
            final RecurrenceFrequency _tmpFrequency;
            _tmpFrequency = __RecurrenceFrequency_stringToEnum(_cursor.getString(_cursorIndexOfFrequency));
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpNextDueDate;
            _tmpNextDueDate = _cursor.getLong(_cursorIndexOfNextDueDate);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new RecurringTransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpFrequency,_tmpStartDate,_tmpNextDueDate,_tmpIsActive,_tmpCreatedAt);
            _result.add(_item);
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

  private String __TransactionType_enumToString(@NonNull final TransactionType _value) {
    switch (_value) {
      case INCOME: return "INCOME";
      case EXPENSE: return "EXPENSE";
      case TRANSFER: return "TRANSFER";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __PaymentMethod_enumToString(@NonNull final PaymentMethod _value) {
    switch (_value) {
      case CASH: return "CASH";
      case UPI: return "UPI";
      case DEBIT_CARD: return "DEBIT_CARD";
      case CREDIT_CARD: return "CREDIT_CARD";
      case NET_BANKING: return "NET_BANKING";
      case OTHER: return "OTHER";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __RecurrenceFrequency_enumToString(@NonNull final RecurrenceFrequency _value) {
    switch (_value) {
      case DAILY: return "DAILY";
      case WEEKLY: return "WEEKLY";
      case MONTHLY: return "MONTHLY";
      case YEARLY: return "YEARLY";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private TransactionType __TransactionType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "INCOME": return TransactionType.INCOME;
      case "EXPENSE": return TransactionType.EXPENSE;
      case "TRANSFER": return TransactionType.TRANSFER;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private PaymentMethod __PaymentMethod_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "CASH": return PaymentMethod.CASH;
      case "UPI": return PaymentMethod.UPI;
      case "DEBIT_CARD": return PaymentMethod.DEBIT_CARD;
      case "CREDIT_CARD": return PaymentMethod.CREDIT_CARD;
      case "NET_BANKING": return PaymentMethod.NET_BANKING;
      case "OTHER": return PaymentMethod.OTHER;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private RecurrenceFrequency __RecurrenceFrequency_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "DAILY": return RecurrenceFrequency.DAILY;
      case "WEEKLY": return RecurrenceFrequency.WEEKLY;
      case "MONTHLY": return RecurrenceFrequency.MONTHLY;
      case "YEARLY": return RecurrenceFrequency.YEARLY;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
