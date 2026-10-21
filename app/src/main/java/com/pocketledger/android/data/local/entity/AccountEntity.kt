package com.pocketledger.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pocketledger.android.core.model.AccountType

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: AccountType,
    val initialBalancePaise: Long = 0L,
    val currencyCode: String = "INR",
    val isExcludedFromNetWorth: Boolean = false,
    val iconName: String = "account_balance",
    val colorHex: String = "#1E88E5",
    val createdAt: Long = System.currentTimeMillis()
)
