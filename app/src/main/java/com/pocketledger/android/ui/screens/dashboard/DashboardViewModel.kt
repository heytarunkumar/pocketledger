package com.pocketledger.android.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.data.local.entity.TransactionWithDetails
import com.pocketledger.android.data.repository.PocketLedgerRepository
import com.pocketledger.android.domain.FinancialCalculator
import com.pocketledger.android.ui.screens.accounts.AccountWithCalculatedBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val totalAvailableBalancePaise: Long = 0L,
    val totalIncomePaise: Long = 0L,
    val totalExpensePaise: Long = 0L,
    val netCashFlowPaise: Long = 0L,
    val savingsRatePercentage: Double = 0.0,
    val recentTransactions: List<TransactionWithDetails> = emptyList(),
    val accountsWithBalances: List<AccountWithCalculatedBalance> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: PocketLedgerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            combine(
                repository.getAllAccounts(),
                repository.getAllTransactionsWithDetails()
            ) { accounts, txWithDetails ->
                val transactions = txWithDetails.map { it.transaction }
                
                val totalBalance = FinancialCalculator.calculateTotalAvailableBalance(accounts, transactions)
                val income = FinancialCalculator.calculateTotalIncome(transactions)
                val expense = FinancialCalculator.calculateTotalExpense(transactions)
                val netCashFlow = FinancialCalculator.calculateNetCashFlow(transactions)
                val savingsRate = FinancialCalculator.calculateSavingsRate(transactions)

                val accountBalances = accounts.map { acc ->
                    val bal = FinancialCalculator.calculateAccountBalance(acc, transactions)
                    AccountWithCalculatedBalance(acc, bal)
                }

                DashboardUiState(
                    totalAvailableBalancePaise = totalBalance,
                    totalIncomePaise = income,
                    totalExpensePaise = expense,
                    netCashFlowPaise = netCashFlow,
                    savingsRatePercentage = savingsRate,
                    recentTransactions = txWithDetails.take(10),
                    accountsWithBalances = accountBalances,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
