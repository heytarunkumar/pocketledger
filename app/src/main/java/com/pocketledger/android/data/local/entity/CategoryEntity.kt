package com.pocketledger.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pocketledger.android.core.model.CategoryType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: CategoryType,
    val parentId: Long? = null,
    val iconName: String = "category",
    val colorHex: String = "#43A047",
    val isDefault: Boolean = false,
    val isDisabled: Boolean = false
)
