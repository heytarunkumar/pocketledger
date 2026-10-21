package com.pocketledger.android.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pocketledger.android.core.model.PaymentMethod
import com.pocketledger.android.core.model.RecurrenceFrequency
import com.pocketledger.android.core.model.TransactionType

@Entity(
    tableName = "recurring_transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("accountId"),
        Index("categoryId")
    ]
)
data class RecurringTransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TransactionType,
    val amountPaise: Long,
    val note: String = "",
    val accountId: Long,
    val transferDestinationAccountId: Long? = null,
    val categoryId: Long? = null,
    val paymentMethod: PaymentMethod = PaymentMethod.OTHER,
    val frequency: RecurrenceFrequency = RecurrenceFrequency.MONTHLY,
    val startDate: Long,
    val nextDueDate: Long,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
