package com.pocketledger.android.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pocketledger.android.core.model.AccountType;
import com.pocketledger.android.core.model.CategoryType;
import com.pocketledger.android.core.model.PaymentMethod;
import com.pocketledger.android.core.model.TransactionType;
import com.pocketledger.android.data.local.entity.AccountEntity;
import com.pocketledger.android.data.local.entity.CategoryEntity;
import com.pocketledger.android.data.local.entity.TransactionEntity;
import com.pocketledger.android.data.local.entity.TransactionWithDetails;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
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
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransactionEntity> __insertionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __deletionAdapterOfTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionEntity> __updateAdapterOfTransactionEntity;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactionEntity = new EntityInsertionAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transactions` (`id`,`type`,`amountPaise`,`timestamp`,`note`,`accountId`,`transferDestinationAccountId`,`categoryId`,`paymentMethod`,`tags`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __TransactionType_enumToString(entity.getType()));
        statement.bindLong(3, entity.getAmountPaise());
        statement.bindLong(4, entity.getTimestamp());
        statement.bindString(5, entity.getNote());
        statement.bindLong(6, entity.getAccountId());
        if (entity.getTransferDestinationAccountId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getTransferDestinationAccountId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getCategoryId());
        }
        statement.bindString(9, __PaymentMethod_enumToString(entity.getPaymentMethod()));
        statement.bindString(10, entity.getTags());
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `transactions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTransactionEntity = new EntityDeletionOrUpdateAdapter<TransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `id` = ?,`type` = ?,`amountPaise` = ?,`timestamp` = ?,`note` = ?,`accountId` = ?,`transferDestinationAccountId` = ?,`categoryId` = ?,`paymentMethod` = ?,`tags` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, __TransactionType_enumToString(entity.getType()));
        statement.bindLong(3, entity.getAmountPaise());
        statement.bindLong(4, entity.getTimestamp());
        statement.bindString(5, entity.getNote());
        statement.bindLong(6, entity.getAccountId());
        if (entity.getTransferDestinationAccountId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getTransferDestinationAccountId());
        }
        if (entity.getCategoryId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getCategoryId());
        }
        statement.bindString(9, __PaymentMethod_enumToString(entity.getPaymentMethod()));
        statement.bindString(10, entity.getTags());
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getUpdatedAt());
        statement.bindLong(13, entity.getId());
      }
    };
  }

  @Override
  public Object insertTransaction(final TransactionEntity transaction,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTransactionEntity.insertAndReturnId(transaction);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTransaction(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTransaction(final TransactionEntity transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTransactionEntity.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TransactionWithDetails>> getAllTransactionsWithDetails() {
    final String _sql = "SELECT * FROM transactions ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"accounts", "categories",
        "transactions"}, new Callable<List<TransactionWithDetails>>() {
      @Override
      @NonNull
      public List<TransactionWithDetails> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
            final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
            final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
            final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
            final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
            final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
            final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final LongSparseArray<AccountEntity> _collectionAccount = new LongSparseArray<AccountEntity>();
            final LongSparseArray<AccountEntity> _collectionDestinationAccount = new LongSparseArray<AccountEntity>();
            final LongSparseArray<CategoryEntity> _collectionCategory = new LongSparseArray<CategoryEntity>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfAccountId);
              _collectionAccount.put(_tmpKey, null);
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfTransferDestinationAccountId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfTransferDestinationAccountId);
              }
              if (_tmpKey_1 != null) {
                _collectionDestinationAccount.put(_tmpKey_1, null);
              }
              final Long _tmpKey_2;
              if (_cursor.isNull(_cursorIndexOfCategoryId)) {
                _tmpKey_2 = null;
              } else {
                _tmpKey_2 = _cursor.getLong(_cursorIndexOfCategoryId);
              }
              if (_tmpKey_2 != null) {
                _collectionCategory.put(_tmpKey_2, null);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity(_collectionAccount);
            __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity_1(_collectionDestinationAccount);
            __fetchRelationshipcategoriesAscomPocketledgerAndroidDataLocalEntityCategoryEntity(_collectionCategory);
            final List<TransactionWithDetails> _result = new ArrayList<TransactionWithDetails>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final TransactionWithDetails _item;
              final TransactionEntity _tmpTransaction;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final TransactionType _tmpType;
              _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
              final long _tmpAmountPaise;
              _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
              final long _tmpTimestamp;
              _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
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
              final String _tmpTags;
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              _tmpTransaction = new TransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpTimestamp,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
              final AccountEntity _tmpAccount;
              final long _tmpKey_3;
              _tmpKey_3 = _cursor.getLong(_cursorIndexOfAccountId);
              _tmpAccount = _collectionAccount.get(_tmpKey_3);
              final AccountEntity _tmpDestinationAccount;
              final Long _tmpKey_4;
              if (_cursor.isNull(_cursorIndexOfTransferDestinationAccountId)) {
                _tmpKey_4 = null;
              } else {
                _tmpKey_4 = _cursor.getLong(_cursorIndexOfTransferDestinationAccountId);
              }
              if (_tmpKey_4 != null) {
                _tmpDestinationAccount = _collectionDestinationAccount.get(_tmpKey_4);
              } else {
                _tmpDestinationAccount = null;
              }
              final CategoryEntity _tmpCategory;
              final Long _tmpKey_5;
              if (_cursor.isNull(_cursorIndexOfCategoryId)) {
                _tmpKey_5 = null;
              } else {
                _tmpKey_5 = _cursor.getLong(_cursorIndexOfCategoryId);
              }
              if (_tmpKey_5 != null) {
                _tmpCategory = _collectionCategory.get(_tmpKey_5);
              } else {
                _tmpCategory = null;
              }
              _item = new TransactionWithDetails(_tmpTransaction,_tmpAccount,_tmpDestinationAccount,_tmpCategory);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<TransactionEntity>> getAllTransactions() {
    final String _sql = "SELECT * FROM transactions ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final TransactionType _tmpType;
            _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpAmountPaise;
            _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
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
            final String _tmpTags;
            _tmpTags = _cursor.getString(_cursorIndexOfTags);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpTimestamp,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<TransactionEntity>> getTransactionsForPeriod(final long startTime,
      final long endTime) {
    final String _sql = "SELECT * FROM transactions WHERE timestamp >= ? AND timestamp <= ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final TransactionType _tmpType;
            _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpAmountPaise;
            _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
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
            final String _tmpTags;
            _tmpTags = _cursor.getString(_cursorIndexOfTags);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpTimestamp,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<TransactionEntity>> getTransactionsForAccount(final long accountId) {
    final String _sql = "SELECT * FROM transactions WHERE accountId = ? OR transferDestinationAccountId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, accountId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, accountId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transactions"}, new Callable<List<TransactionEntity>>() {
      @Override
      @NonNull
      public List<TransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<TransactionEntity> _result = new ArrayList<TransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final TransactionType _tmpType;
            _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpAmountPaise;
            _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
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
            final String _tmpTags;
            _tmpTags = _cursor.getString(_cursorIndexOfTags);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new TransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpTimestamp,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getTransactionById(final long id,
      final Continuation<? super TransactionEntity> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TransactionEntity>() {
      @Override
      @Nullable
      public TransactionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
          final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final TransactionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final TransactionType _tmpType;
            _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final long _tmpAmountPaise;
            _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
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
            final String _tmpTags;
            _tmpTags = _cursor.getString(_cursorIndexOfTags);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new TransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpTimestamp,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Object getTransactionWithDetailsById(final long id,
      final Continuation<? super TransactionWithDetails> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, true, _cancellationSignal, new Callable<TransactionWithDetails>() {
      @Override
      @Nullable
      public TransactionWithDetails call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
            final int _cursorIndexOfAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaise");
            final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final int _cursorIndexOfAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "accountId");
            final int _cursorIndexOfTransferDestinationAccountId = CursorUtil.getColumnIndexOrThrow(_cursor, "transferDestinationAccountId");
            final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
            final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
            final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final LongSparseArray<AccountEntity> _collectionAccount = new LongSparseArray<AccountEntity>();
            final LongSparseArray<AccountEntity> _collectionDestinationAccount = new LongSparseArray<AccountEntity>();
            final LongSparseArray<CategoryEntity> _collectionCategory = new LongSparseArray<CategoryEntity>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfAccountId);
              _collectionAccount.put(_tmpKey, null);
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfTransferDestinationAccountId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfTransferDestinationAccountId);
              }
              if (_tmpKey_1 != null) {
                _collectionDestinationAccount.put(_tmpKey_1, null);
              }
              final Long _tmpKey_2;
              if (_cursor.isNull(_cursorIndexOfCategoryId)) {
                _tmpKey_2 = null;
              } else {
                _tmpKey_2 = _cursor.getLong(_cursorIndexOfCategoryId);
              }
              if (_tmpKey_2 != null) {
                _collectionCategory.put(_tmpKey_2, null);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity(_collectionAccount);
            __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity_1(_collectionDestinationAccount);
            __fetchRelationshipcategoriesAscomPocketledgerAndroidDataLocalEntityCategoryEntity(_collectionCategory);
            final TransactionWithDetails _result;
            if (_cursor.moveToFirst()) {
              final TransactionEntity _tmpTransaction;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final TransactionType _tmpType;
              _tmpType = __TransactionType_stringToEnum(_cursor.getString(_cursorIndexOfType));
              final long _tmpAmountPaise;
              _tmpAmountPaise = _cursor.getLong(_cursorIndexOfAmountPaise);
              final long _tmpTimestamp;
              _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
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
              final String _tmpTags;
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              _tmpTransaction = new TransactionEntity(_tmpId,_tmpType,_tmpAmountPaise,_tmpTimestamp,_tmpNote,_tmpAccountId,_tmpTransferDestinationAccountId,_tmpCategoryId,_tmpPaymentMethod,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
              final AccountEntity _tmpAccount;
              final long _tmpKey_3;
              _tmpKey_3 = _cursor.getLong(_cursorIndexOfAccountId);
              _tmpAccount = _collectionAccount.get(_tmpKey_3);
              final AccountEntity _tmpDestinationAccount;
              final Long _tmpKey_4;
              if (_cursor.isNull(_cursorIndexOfTransferDestinationAccountId)) {
                _tmpKey_4 = null;
              } else {
                _tmpKey_4 = _cursor.getLong(_cursorIndexOfTransferDestinationAccountId);
              }
              if (_tmpKey_4 != null) {
                _tmpDestinationAccount = _collectionDestinationAccount.get(_tmpKey_4);
              } else {
                _tmpDestinationAccount = null;
              }
              final CategoryEntity _tmpCategory;
              final Long _tmpKey_5;
              if (_cursor.isNull(_cursorIndexOfCategoryId)) {
                _tmpKey_5 = null;
              } else {
                _tmpKey_5 = _cursor.getLong(_cursorIndexOfCategoryId);
              }
              if (_tmpKey_5 != null) {
                _tmpCategory = _collectionCategory.get(_tmpKey_5);
              } else {
                _tmpCategory = null;
              }
              _result = new TransactionWithDetails(_tmpTransaction,_tmpAccount,_tmpDestinationAccount,_tmpCategory);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
            _statement.release();
          }
        } finally {
          __db.endTransaction();
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

  private void __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity(
      @NonNull final LongSparseArray<AccountEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`type`,`initialBalancePaise`,`currencyCode`,`isExcludedFromNetWorth`,`iconName`,`colorHex`,`createdAt` FROM `accounts` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfType = 2;
      final int _cursorIndexOfInitialBalancePaise = 3;
      final int _cursorIndexOfCurrencyCode = 4;
      final int _cursorIndexOfIsExcludedFromNetWorth = 5;
      final int _cursorIndexOfIconName = 6;
      final int _cursorIndexOfColorHex = 7;
      final int _cursorIndexOfCreatedAt = 8;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final AccountEntity _item_1;
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
          _item_1 = new AccountEntity(_tmpId,_tmpName,_tmpType,_tmpInitialBalancePaise,_tmpCurrencyCode,_tmpIsExcludedFromNetWorth,_tmpIconName,_tmpColorHex,_tmpCreatedAt);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity_1(
      @NonNull final LongSparseArray<AccountEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipaccountsAscomPocketledgerAndroidDataLocalEntityAccountEntity_1(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`type`,`initialBalancePaise`,`currencyCode`,`isExcludedFromNetWorth`,`iconName`,`colorHex`,`createdAt` FROM `accounts` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfType = 2;
      final int _cursorIndexOfInitialBalancePaise = 3;
      final int _cursorIndexOfCurrencyCode = 4;
      final int _cursorIndexOfIsExcludedFromNetWorth = 5;
      final int _cursorIndexOfIconName = 6;
      final int _cursorIndexOfColorHex = 7;
      final int _cursorIndexOfCreatedAt = 8;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final AccountEntity _item_1;
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
          _item_1 = new AccountEntity(_tmpId,_tmpName,_tmpType,_tmpInitialBalancePaise,_tmpCurrencyCode,_tmpIsExcludedFromNetWorth,_tmpIconName,_tmpColorHex,_tmpCreatedAt);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private CategoryType __CategoryType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "INCOME": return CategoryType.INCOME;
      case "EXPENSE": return CategoryType.EXPENSE;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private void __fetchRelationshipcategoriesAscomPocketledgerAndroidDataLocalEntityCategoryEntity(
      @NonNull final LongSparseArray<CategoryEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipcategoriesAscomPocketledgerAndroidDataLocalEntityCategoryEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`type`,`parentId`,`iconName`,`colorHex`,`isDefault`,`isDisabled` FROM `categories` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfType = 2;
      final int _cursorIndexOfParentId = 3;
      final int _cursorIndexOfIconName = 4;
      final int _cursorIndexOfColorHex = 5;
      final int _cursorIndexOfIsDefault = 6;
      final int _cursorIndexOfIsDisabled = 7;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final CategoryEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final String _tmpName;
          _tmpName = _cursor.getString(_cursorIndexOfName);
          final CategoryType _tmpType;
          _tmpType = __CategoryType_stringToEnum(_cursor.getString(_cursorIndexOfType));
          final Long _tmpParentId;
          if (_cursor.isNull(_cursorIndexOfParentId)) {
            _tmpParentId = null;
          } else {
            _tmpParentId = _cursor.getLong(_cursorIndexOfParentId);
          }
          final String _tmpIconName;
          _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
          final String _tmpColorHex;
          _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
          final boolean _tmpIsDefault;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsDefault);
          _tmpIsDefault = _tmp != 0;
          final boolean _tmpIsDisabled;
          final int _tmp_1;
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsDisabled);
          _tmpIsDisabled = _tmp_1 != 0;
          _item_1 = new CategoryEntity(_tmpId,_tmpName,_tmpType,_tmpParentId,_tmpIconName,_tmpColorHex,_tmpIsDefault,_tmpIsDisabled);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
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
}
