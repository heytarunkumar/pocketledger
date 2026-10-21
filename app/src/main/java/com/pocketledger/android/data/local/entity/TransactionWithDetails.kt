package com.pocketledger.android.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithDetails(
    @Embedded val transaction: TransactionEntity,

    @Relation(
        parentColumn = "accountId",
        entityColumn = "id"
    )
    val account: AccountEntity,

    @Relation(
        parentColumn = "transferDestinationAccountId",
        entityColumn = "id"
    )
    val destinationAccount: AccountEntity?,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?
)
