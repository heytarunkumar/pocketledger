package com.pocketledger.android.ui.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.core.model.TransactionType
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.TransactionWithDetails
import com.pocketledger.android.data.repository.PocketLedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOption {
    DATE_NEWEST,
    DATE_OLDEST,
    AMOUNT_HIGHEST,
    AMOUNT_LOWEST
}

data class TransactionListUiState(
    val searchQuery: String = "",
    val selectedTypeFilter: TransactionType? = null,
    val selectedAccountId: Long? = null,
    val selectedCategoryId: Long? = null,
    val selectedSortOption: SortOption = SortOption.DATE_NEWEST,
    val accounts: List<AccountEntity> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val filteredTransactions: List<TransactionWithDetails> = emptyList(),
    val totalCount: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val repository: PocketLedgerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionListUiState())
    val uiState: StateFlow<TransactionListUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                repository.getAllTransactionsWithDetails(),
                repository.getAllAccounts(),
                repository.getAllActiveCategories(),
                _uiState
            ) { allTx, accounts, categories, state ->
                val query = state.searchQuery.trim().lowercase()

                var filtered = allTx.filter { item ->
                    val tx = item.transaction
                    // Query Filter
                    val matchesQuery = query.isEmpty() ||
                            tx.note.lowercase().contains(query) ||
                            tx.tags.lowercase().contains(query) ||
                            item.account.name.lowercase().contains(query) ||
                            (item.category?.name?.lowercase()?.contains(query) == true) ||
                            (item.destinationAccount?.name?.lowercase()?.contains(query) == true)

                    // Type Filter
                    val matchesType = state.selectedTypeFilter == null || tx.type == state.selectedTypeFilter

                    // Account Filter
                    val matchesAccount = state.selectedAccountId == null ||
                            tx.accountId == state.selectedAccountId ||
                            tx.transferDestinationAccountId == state.selectedAccountId

                    // Category Filter
                    val matchesCategory = state.selectedCategoryId == null || tx.categoryId == state.selectedCategoryId

                    matchesQuery && matchesType && matchesAccount && matchesCategory
                }

                // Sorting
                filtered = when (state.selectedSortOption) {
                    SortOption.DATE_NEWEST -> filtered.sortedByDescending { it.transaction.timestamp }
                    SortOption.DATE_OLDEST -> filtered.sortedBy { it.transaction.timestamp }
                    SortOption.AMOUNT_HIGHEST -> filtered.sortedByDescending { it.transaction.amountPaise }
                    SortOption.AMOUNT_LOWEST -> filtered.sortedBy { it.transaction.amountPaise }
                }

                state.copy(
                    accounts = accounts,
                    categories = categories,
                    filteredTransactions = filtered,
                    totalCount = filtered.size,
                    isLoading = false
                )
            }.collect { updatedState ->
                _uiState.value = updatedState
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun onTypeFilterSelected(type: TransactionType?) {
        _uiState.value = _uiState.value.copy(selectedTypeFilter = type)
    }

    fun onAccountFilterSelected(accountId: Long?) {
        _uiState.value = _uiState.value.copy(selectedAccountId = accountId)
    }

    fun onCategoryFilterSelected(categoryId: Long?) {
        _uiState.value = _uiState.value.copy(selectedCategoryId = categoryId)
    }

    fun onSortOptionSelected(sortOption: SortOption) {
        _uiState.value = _uiState.value.copy(selectedSortOption = sortOption)
    }

    fun clearAllFilters() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            selectedTypeFilter = null,
            selectedAccountId = null,
            selectedCategoryId = null,
            selectedSortOption = SortOption.DATE_NEWEST
        )
    }
}
