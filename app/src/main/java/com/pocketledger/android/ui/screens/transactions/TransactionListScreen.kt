package com.pocketledger.android.ui.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pocketledger.android.core.model.TransactionType
import com.pocketledger.android.core.util.CurrencyUtils
import com.pocketledger.android.data.local.entity.TransactionWithDetails
import com.pocketledger.android.ui.theme.ExpenseRed
import com.pocketledger.android.ui.theme.IncomeGreen
import com.pocketledger.android.ui.theme.TransferPurple
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TransactionListScreen(
    viewModel: TransactionListViewModel,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToEditTransaction: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions (${uiState.totalCount})") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddTransaction) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // 1. Search Bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChanged,
                placeholder = { Text("Search by note, category, or account...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = if (uiState.searchQuery.isNotEmpty()) {
                    {
                        IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear search")
                        }
                    }
                } else null,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // 2. Filters & Sorting Row
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Type Filter Chips
                FilterChip(
                    selected = uiState.selectedTypeFilter == null,
                    onClick = { viewModel.onTypeFilterSelected(null) },
                    label = { Text("All Types") }
                )
                TransactionType.entries.forEach { type ->
                    FilterChip(
                        selected = uiState.selectedTypeFilter == type,
                        onClick = { viewModel.onTypeFilterSelected(type) },
                        label = { Text(type.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sort Dropdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var sortExpanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = sortExpanded,
                    onExpandedChange = { sortExpanded = !sortExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = "Sort: " + uiState.selectedSortOption.name.replace("_", " "),
                        onValueChange = {},
                        readOnly = true,
                        leadingIcon = { Icon(Icons.Default.FilterList, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sortExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = sortExpanded,
                        onDismissRequest = { sortExpanded = false }
                    ) {
                        SortOption.entries.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.name.replace("_", " ")) },
                                onClick = {
                                    viewModel.onSortOptionSelected(option)
                                    sortExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Transactions List
            if (uiState.filteredTransactions.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No matching transactions found.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.filteredTransactions, key = { it.transaction.id }) { item ->
                        TransactionListItem(
                            item = item,
                            onClick = { onNavigateToEditTransaction(item.transaction.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionListItem(
    item: TransactionWithDetails,
    onClick: () -> Unit
) {
    val tx = item.transaction
    val (icon, tint, prefix) = when (tx.type) {
        TransactionType.INCOME -> Triple(Icons.Default.ArrowUpward, IncomeGreen, "+ ")
        TransactionType.EXPENSE -> Triple(Icons.Default.ArrowDownward, ExpenseRed, "- ")
        TransactionType.TRANSFER -> Triple(Icons.Default.SwapHoriz, TransferPurple, "")
    }

    val dateFormat = SimpleDateFormat("dd MMM, yyyy • hh:mm a", Locale.getDefault())
    val dateString = dateFormat.format(Date(tx.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(icon, contentDescription = null, tint = tint)
                Column {
                    val title = when (tx.type) {
                        TransactionType.TRANSFER -> "Transfer: ${item.account.name} -> ${item.destinationAccount?.name ?: "Unknown"}"
                        else -> item.category?.name ?: item.account.name
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    val subtitle = if (tx.note.isNotEmpty()) "${item.account.name} • ${tx.note}" else "${item.account.name} • ${tx.paymentMethod.name}"
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = prefix + CurrencyUtils.formatPaiseToRupees(tx.amountPaise),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = tint
                )
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
            }
        }
    }
}
