package com.pocketledger.android.ui.screens.budgets

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pocketledger.android.core.util.CurrencyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetListScreen(
    viewModel: BudgetListViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    if (uiState.showAddEditBudgetDialog) {
        AddEditBudgetDialog(
            categories = uiState.expenseCategories,
            budgetToEdit = uiState.budgetToEdit,
            onDismiss = viewModel::onDismissBudgetDialog,
            onSaveBudget = viewModel::saveBudget
        )
    }

    if (uiState.showAddEditGoalDialog) {
        AddEditSavingsGoalDialog(
            goalToEdit = uiState.goalToEdit,
            onDismiss = viewModel::onDismissGoalDialog,
            onSaveGoal = viewModel::saveSavingsGoal
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budgets & Savings") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedTabIndex == 0) viewModel.onAddBudgetClicked()
                    else viewModel.onAddGoalClicked()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Category Budgets") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Savings Goals") }
                )
            }

            if (selectedTabIndex == 0) {
                // Budgets List
                if (uiState.categoryBudgets.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No category budgets set for this month.\nTap + to create a budget limit!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.categoryBudgets, key = { it.budget.id }) { item ->
                            val b = item.budget
                            val progress = (item.utilizationPercentage / 100f).coerceAtMost(1.0f)
                            val color = if (item.isOverspent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = item.category.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Row {
                                            IconButton(onClick = { viewModel.onEditBudgetClicked(b) }) {
                                                Icon(Icons.Default.Edit, contentDescription = "Edit Budget")
                                            }
                                            IconButton(onClick = { viewModel.deleteBudget(b) }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Delete Budget")
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Spent: ${CurrencyUtils.formatPaiseToRupees(item.spentAmountPaise)}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = color
                                        )
                                        Text(
                                            text = "Limit: ${CurrencyUtils.formatPaiseToRupees(b.amountPaise)}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    LinearProgressIndicator(
                                        progress = { progress },
                                        color = color,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    if (item.isOverspent) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Warning,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                            val overspentAmount = item.spentAmountPaise - b.amountPaise
                                            Text(
                                                text = "Overspent by ${CurrencyUtils.formatPaiseToRupees(overspentAmount)}!",
                                                style = MaterialTheme.typography.labelLarge,
                                                color = MaterialTheme.colorScheme.error,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Savings Goals List
                if (uiState.savingsGoals.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No savings goals created yet.\nTap + to set a new savings target!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.savingsGoals, key = { it.id }) { goal ->
                            val progress = if (goal.targetAmountPaise > 0) {
                                (goal.currentSavedPaise.toFloat() / goal.targetAmountPaise.toFloat()).coerceAtMost(1.0f)
                            } else 0f

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = goal.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Row {
                                            IconButton(onClick = { viewModel.onEditGoalClicked(goal) }) {
                                                Icon(Icons.Default.Edit, contentDescription = "Edit Goal")
                                            }
                                            IconButton(onClick = { viewModel.deleteSavingsGoal(goal) }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Delete Goal")
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Saved: ${CurrencyUtils.formatPaiseToRupees(goal.currentSavedPaise)}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Target: ${CurrencyUtils.formatPaiseToRupees(goal.targetAmountPaise)}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    LinearProgressIndicator(
                                        progress = { progress },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
