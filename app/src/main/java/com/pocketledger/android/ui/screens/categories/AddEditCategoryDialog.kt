package com.pocketledger.android.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.data.local.entity.CategoryEntity

@Composable
fun AddEditCategoryDialog(
    categoryToEdit: CategoryEntity? = null,
    defaultType: CategoryType = CategoryType.EXPENSE,
    onDismiss: () -> Unit,
    onSaveCategory: (name: String, type: CategoryType) -> Unit
) {
    var name by remember { mutableStateOf(categoryToEdit?.name ?: "") }
    var categoryType by remember { mutableStateOf(categoryToEdit?.type ?: defaultType) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (categoryToEdit != null) "Edit Category" else "Add New Category")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategoryType.entries.forEach { type ->
                        FilterChip(
                            selected = categoryType == type,
                            onClick = { categoryType = type },
                            label = { Text(type.name) }
                        )
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; errorMessage = null },
                    label = { Text("Category Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                errorMessage?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val trimmedName = name.trim()
                    if (trimmedName.isEmpty()) {
                        errorMessage = "Category name cannot be empty."
                        return@TextButton
                    }
                    onSaveCategory(trimmedName, categoryType)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
