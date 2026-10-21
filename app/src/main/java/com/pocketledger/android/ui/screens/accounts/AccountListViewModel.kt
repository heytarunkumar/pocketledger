package com.pocketledger.android.ui.screens.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.core.model.AccountType
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.repository.PocketLedgerRepository
import com.pocketledger.android.domain.FinancialCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountWithCalculatedBalance(
    val account: AccountEntity,
    val currentBalancePaise: Long
)

data class AccountListUiState(
    val accountsWithBalances: List<AccountWithCalculatedBalance> = emptyList(),
    val totalAvailableBalancePaise: Long = 0L,
    val isLoading: Boolean = true,
    val accountToEdit: AccountEntity? = null,
    val showAddEditDialog: Boolean = false
)

@HiltViewModel
class AccountListViewModel @Inject constructor(
    private val repository: PocketLedgerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountListUiState())
    val uiState: StateFlow<AccountListUiState> = _uiState.asStateFlow()

    init {
        loadAccountsData()
    }

    private fun loadAccountsData() {
        viewModelScope.launch {
            combine(
                repository.getAllAccounts(),
                repository.getAllTransactions()
            ) { accounts, transactions ->
                val calculatedAccounts = accounts.map { acc ->
                    val balance = FinancialCalculator.calculateAccountBalance(acc, transactions)
                    AccountWithCalculatedBalance(acc, balance)
                }

                val totalBalance = FinancialCalculator.calculateTotalAvailableBalance(accounts, transactions)

                _uiState.value.copy(
                    accountsWithBalances = calculatedAccounts,
                    totalAvailableBalancePaise = totalBalance,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun onAddAccountClicked() {
        _uiState.value = _uiState.value.copy(
            accountToEdit = null,
            showAddEditDialog = true
        )
    }

    fun onEditAccountClicked(account: AccountEntity) {
        _uiState.value = _uiState.value.copy(
            accountToEdit = account,
            showAddEditDialog = true
        )
    }

    fun onDismissDialog() {
        _uiState.value = _uiState.value.copy(
            showAddEditDialog = false,
            accountToEdit = null
        )
    }

    fun saveAccount(name: String, type: AccountType, initialBalancePaise: Long, isExcluded: Boolean) {
        viewModelScope.launch {
            val existing = _uiState.value.accountToEdit
            if (existing != null) {
                repository.updateAccount(
                    existing.copy(
                        name = name,
                        type = type,
                        initialBalancePaise = initialBalancePaise,
                        isExcludedFromNetWorth = isExcluded
                    )
                )
            } else {
                repository.insertAccount(
                    AccountEntity(
                        name = name,
                        type = type,
                        initialBalancePaise = initialBalancePaise,
                        isExcludedFromNetWorth = isExcluded
                    )
                )
            }
            onDismissDialog()
        }
    }

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
}
