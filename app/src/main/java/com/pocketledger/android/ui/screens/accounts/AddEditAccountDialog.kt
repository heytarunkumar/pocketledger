package com.pocketledger.android.ui.screens.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pocketledger.android.core.model.AccountType
import com.pocketledger.android.core.util.CurrencyUtils
import com.pocketledger.android.data.local.entity.AccountEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAccountDialog(
    accountToEdit: AccountEntity? = null,
    onDismiss: () -> Unit,
    onSaveAccount: (name: String, type: AccountType, initialBalancePaise: Long, isExcluded: Boolean) -> Unit
) {
    var name by remember { mutableStateOf(accountToEdit?.name ?: "") }
    var accountType by remember { mutableStateOf(accountToEdit?.type ?: AccountType.BANK) }
    var initialBalanceInput by remember {
        mutableStateOf(accountToEdit?.let { CurrencyUtils.formatPaiseToRupees(it.initialBalancePaise, showSymbol = false) } ?: "0")
    }
    var isExcludedFromNetWorth by remember { mutableStateOf(accountToEdit?.isExcludedFromNetWorth ?: false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var typeExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (accountToEdit != null) "Edit Account" else "Add New Account")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; errorMessage = null },
                    label = { Text("Account Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = { typeExpanded = !typeExpanded }
                ) {
                    OutlinedTextField(
                        value = accountType.name.replace("_", " "),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Account Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = typeExpanded,
                        onDismissRequest = { typeExpanded = false }
                    ) {
                        AccountType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.name.replace("_", " ")) },
                                onClick = {
                                    accountType = type
                                    typeExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = initialBalanceInput,
                    onValueChange = { initialBalanceInput = it; errorMessage = null },
                    label = { Text("Initial Balance (₹)") },
                    prefix = { Text("₹ ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isExcludedFromNetWorth,
                        onCheckedChange = { isExcludedFromNetWorth = it }
                    )
                    Text(
                        text = "Exclude from Net Worth",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

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
                        errorMessage = "Account name cannot be empty."
                        return@TextButton
                    }

                    val paise = CurrencyUtils.parseRupeesToPaise(initialBalanceInput) ?: 0L
                    onSaveAccount(trimmedName, accountType, paise, isExcludedFromNetWorth)
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
