package com.pocketledger.android.ui.screens.budgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pocketledger.android.core.util.CurrencyUtils
import com.pocketledger.android.data.local.entity.SavingsGoalEntity

@Composable
fun AddEditSavingsGoalDialog(
    goalToEdit: SavingsGoalEntity? = null,
    onDismiss: () -> Unit,
    onSaveGoal: (title: String, targetPaise: Long, currentSavedPaise: Long) -> Unit
) {
    var title by remember { mutableStateOf(goalToEdit?.title ?: "") }
    var targetInput by remember {
        mutableStateOf(goalToEdit?.let { CurrencyUtils.formatPaiseToRupees(it.targetAmountPaise, showSymbol = false) } ?: "")
    }
    var currentSavedInput by remember {
        mutableStateOf(goalToEdit?.let { CurrencyUtils.formatPaiseToRupees(it.currentSavedPaise, showSymbol = false) } ?: "0")
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (goalToEdit != null) "Edit Savings Goal" else "New Savings Goal")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it; errorMessage = null },
                    label = { Text("Goal Title") },
                    placeholder = { Text("e.g., Emergency Fund, Vacation") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = targetInput,
                    onValueChange = { targetInput = it; errorMessage = null },
                    label = { Text("Target Amount (₹)") },
                    prefix = { Text("₹ ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = currentSavedInput,
                    onValueChange = { currentSavedInput = it; errorMessage = null },
                    label = { Text("Current Saved (₹)") },
                    prefix = { Text("₹ ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                    val trimmedTitle = title.trim()
                    if (trimmedTitle.isEmpty()) {
                        errorMessage = "Goal title cannot be empty."
                        return@TextButton
                    }
                    val targetPaise = CurrencyUtils.parseRupeesToPaise(targetInput)
                    if (targetPaise == null || targetPaise <= 0) {
                        errorMessage = "Please enter a valid target amount greater than ₹0."
                        return@TextButton
                    }
                    val savedPaise = CurrencyUtils.parseRupeesToPaise(currentSavedInput) ?: 0L

                    onSaveGoal(trimmedTitle, targetPaise, savedPaise)
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
