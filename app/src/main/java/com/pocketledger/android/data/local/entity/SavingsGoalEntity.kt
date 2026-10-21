package com.pocketledger.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savings_goals")
data class SavingsGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val targetAmountPaise: Long,
    val currentSavedPaise: Long = 0L,
    val targetDate: Long? = null,
    val colorHex: String = "#1E88E5",
    val iconName: String = "savings",
    val createdAt: Long = System.currentTimeMillis()
)
