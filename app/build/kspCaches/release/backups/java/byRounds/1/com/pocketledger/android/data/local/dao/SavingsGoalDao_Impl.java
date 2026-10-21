package com.pocketledger.android.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.pocketledger.android.data.local.entity.SavingsGoalEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class SavingsGoalDao_Impl implements SavingsGoalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SavingsGoalEntity> __insertionAdapterOfSavingsGoalEntity;

  private final EntityDeletionOrUpdateAdapter<SavingsGoalEntity> __deletionAdapterOfSavingsGoalEntity;

  private final EntityDeletionOrUpdateAdapter<SavingsGoalEntity> __updateAdapterOfSavingsGoalEntity;

  public SavingsGoalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSavingsGoalEntity = new EntityInsertionAdapter<SavingsGoalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `savings_goals` (`id`,`title`,`targetAmountPaise`,`currentSavedPaise`,`targetDate`,`colorHex`,`iconName`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsGoalEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getTargetAmountPaise());
        statement.bindLong(4, entity.getCurrentSavedPaise());
        if (entity.getTargetDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getTargetDate());
        }
        statement.bindString(6, entity.getColorHex());
        statement.bindString(7, entity.getIconName());
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfSavingsGoalEntity = new EntityDeletionOrUpdateAdapter<SavingsGoalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `savings_goals` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsGoalEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSavingsGoalEntity = new EntityDeletionOrUpdateAdapter<SavingsGoalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `savings_goals` SET `id` = ?,`title` = ?,`targetAmountPaise` = ?,`currentSavedPaise` = ?,`targetDate` = ?,`colorHex` = ?,`iconName` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsGoalEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getTargetAmountPaise());
        statement.bindLong(4, entity.getCurrentSavedPaise());
        if (entity.getTargetDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getTargetDate());
        }
        statement.bindString(6, entity.getColorHex());
        statement.bindString(7, entity.getIconName());
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insertSavingsGoal(final SavingsGoalEntity goal,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSavingsGoalEntity.insertAndReturnId(goal);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSavingsGoal(final SavingsGoalEntity goal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSavingsGoalEntity.handle(goal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSavingsGoal(final SavingsGoalEntity goal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSavingsGoalEntity.handle(goal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SavingsGoalEntity>> getAllSavingsGoals() {
    final String _sql = "SELECT * FROM savings_goals ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings_goals"}, new Callable<List<SavingsGoalEntity>>() {
      @Override
      @NonNull
      public List<SavingsGoalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTargetAmountPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "targetAmountPaise");
          final int _cursorIndexOfCurrentSavedPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "currentSavedPaise");
          final int _cursorIndexOfTargetDate = CursorUtil.getColumnIndexOrThrow(_cursor, "targetDate");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<SavingsGoalEntity> _result = new ArrayList<SavingsGoalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavingsGoalEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpTargetAmountPaise;
            _tmpTargetAmountPaise = _cursor.getLong(_cursorIndexOfTargetAmountPaise);
            final long _tmpCurrentSavedPaise;
            _tmpCurrentSavedPaise = _cursor.getLong(_cursorIndexOfCurrentSavedPaise);
            final Long _tmpTargetDate;
            if (_cursor.isNull(_cursorIndexOfTargetDate)) {
              _tmpTargetDate = null;
            } else {
              _tmpTargetDate = _cursor.getLong(_cursorIndexOfTargetDate);
            }
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new SavingsGoalEntity(_tmpId,_tmpTitle,_tmpTargetAmountPaise,_tmpCurrentSavedPaise,_tmpTargetDate,_tmpColorHex,_tmpIconName,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
