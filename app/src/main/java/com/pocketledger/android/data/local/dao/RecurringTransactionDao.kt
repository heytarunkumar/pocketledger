package com.pocketledger.android.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pocketledger.android.data.local.entity.RecurringTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecurringTransactionDao {
    @Query("SELECT * FROM recurring_transactions WHERE isActive = 1 ORDER BY nextDueDate ASC")
    fun getAllActiveRecurringTransactions(): Flow<List<RecurringTransactionEntity>>

    @Query("SELECT * FROM recurring_transactions WHERE isActive = 1 AND nextDueDate <= :currentTime")
    suspend fun getDueRecurringTransactions(currentTime: Long): List<RecurringTransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecurringTransaction(entity: RecurringTransactionEntity): Long

    @Update
    suspend fun updateRecurringTransaction(entity: RecurringTransactionEntity)

    @Delete
    suspend fun deleteRecurringTransaction(entity: RecurringTransactionEntity)
}
