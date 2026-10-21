package com.pocketledger.android.ui.screens.accounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pocketledger.android.core.model.AccountType
import com.pocketledger.android.core.util.CurrencyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListScreen(
    viewModel: AccountListViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.showAddEditDialog) {
        AddEditAccountDialog(
            accountToEdit = uiState.accountToEdit,
            onDismiss = viewModel::onDismissDialog,
            onSaveAccount = viewModel::saveAccount
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accounts") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::onAddAccountClicked) {
                Icon(Icons.Default.Add, contentDescription = "Add Account")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Summary Net Worth / Available Balance Header Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Total Available Net Worth",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = CurrencyUtils.formatPaiseToRupees(uiState.totalAvailableBalancePaise),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Your Accounts (${uiState.accountsWithBalances.size})",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(uiState.accountsWithBalances, key = { it.account.id }) { item ->
                val acc = item.account
                val icon = when (acc.type) {
                    AccountType.CASH -> Icons.Default.Money
                    AccountType.BANK -> Icons.Default.AccountBalance
                    AccountType.DIGITAL_WALLET -> Icons.Default.Wallet
                    AccountType.CREDIT_CARD -> Icons.Default.CreditCard
                    AccountType.OTHER -> Icons.Default.AccountBalance
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    text = acc.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                val subtitle = if (acc.isExcludedFromNetWorth) {
                                    "${acc.type.name.replace("_", " ")} • Excluded"
                                } else {
                                    acc.type.name.replace("_", " ")
                                }
                                Text(
                                    text = subtitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = CurrencyUtils.formatPaiseToRupees(item.currentBalancePaise),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Initial: ${CurrencyUtils.formatPaiseToRupees(acc.initialBalancePaise)}",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            IconButton(onClick = { viewModel.onEditAccountClicked(acc) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit Account")
                            }
                        }
                    }
                }
            }
        }
    }
}
