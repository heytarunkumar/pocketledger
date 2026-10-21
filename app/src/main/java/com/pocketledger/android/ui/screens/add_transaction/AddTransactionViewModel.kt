package com.pocketledger.android.ui.screens.add_transaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.core.model.PaymentMethod
import com.pocketledger.android.core.model.TransactionType
import com.pocketledger.android.core.util.CurrencyUtils
import com.pocketledger.android.data.local.entity.AccountEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.TransactionEntity
import com.pocketledger.android.data.repository.PocketLedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddTransactionUiState(
    val editingTransactionId: Long? = null,
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val amountInput: String = "",
    val selectedAccountId: Long? = null,
    val selectedDestinationAccountId: Long? = null,
    val selectedCategoryId: Long? = null,
    val paymentMethod: PaymentMethod = PaymentMethod.UPI,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val accounts: List<AccountEntity> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val errorMessage: String? = null,
    val isSaving: Boolean = false
)

sealed interface AddTransactionEvent {
    data object TransactionSaved : AddTransactionEvent
    data object TransactionDeleted : AddTransactionEvent
}

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: PocketLedgerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val editingId: Long? = savedStateHandle.get<Long>("transactionId")?.takeIf { it > 0 }

    private val _uiState = MutableStateFlow(AddTransactionUiState(editingTransactionId = editingId))
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<AddTransactionEvent>()
    val eventFlow: SharedFlow<AddTransactionEvent> = _eventFlow.asSharedFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getAllAccounts().collect { accounts ->
                _uiState.value = _uiState.value.copy(
                    accounts = accounts,
                    selectedAccountId = _uiState.value.selectedAccountId ?: accounts.firstOrNull()?.id
                )
            }
        }

        editingId?.let { txId ->
            viewModelScope.launch {
                val txWithDetails = repository.getTransactionWithDetailsById(txId)
                if (txWithDetails != null) {
                    val tx = txWithDetails.transaction
                    _uiState.value = _uiState.value.copy(
                        transactionType = tx.type,
                        amountInput = CurrencyUtils.formatPaiseToRupees(tx.amountPaise, showSymbol = false),
                        selectedAccountId = tx.accountId,
                        selectedDestinationAccountId = tx.transferDestinationAccountId,
                        selectedCategoryId = tx.categoryId,
                        paymentMethod = tx.paymentMethod,
                        note = tx.note,
                        timestamp = tx.timestamp
                    )
                    observeCategoriesForType(tx.type)
                } else {
                    observeCategoriesForType(_uiState.value.transactionType)
                }
            }
        } ?: run {
            observeCategoriesForType(_uiState.value.transactionType)
        }
    }

    private fun observeCategoriesForType(type: TransactionType) {
        viewModelScope.launch {
            val categoryType = if (type == TransactionType.INCOME) CategoryType.INCOME else CategoryType.EXPENSE
            repository.getCategoriesByType(categoryType).collect { categories ->
                _uiState.value = _uiState.value.copy(
                    categories = categories,
                    selectedCategoryId = if (type == TransactionType.TRANSFER) null
                    else (_uiState.value.selectedCategoryId ?: categories.firstOrNull()?.id)
                )
            }
        }
    }

    fun onTypeSelected(type: TransactionType) {
        _uiState.value = _uiState.value.copy(
            transactionType = type,
            errorMessage = null
        )
        observeCategoriesForType(type)
    }

    fun onAmountChanged(amount: String) {
        _uiState.value = _uiState.value.copy(amountInput = amount, errorMessage = null)
    }

    fun onAccountSelected(accountId: Long) {
        _uiState.value = _uiState.value.copy(selectedAccountId = accountId, errorMessage = null)
    }

    fun onDestinationAccountSelected(accountId: Long) {
        _uiState.value = _uiState.value.copy(selectedDestinationAccountId = accountId, errorMessage = null)
    }

    fun onCategorySelected(categoryId: Long) {
        _uiState.value = _uiState.value.copy(selectedCategoryId = categoryId, errorMessage = null)
    }

    fun onPaymentMethodSelected(method: PaymentMethod) {
        _uiState.value = _uiState.value.copy(paymentMethod = method)
    }

    fun onNoteChanged(note: String) {
        _uiState.value = _uiState.value.copy(note = note)
    }

    fun saveTransaction() {
        val currentState = _uiState.value
        val paise = CurrencyUtils.parseRupeesToPaise(currentState.amountInput)

        if (paise == null || paise <= 0) {
            _uiState.value = currentState.copy(errorMessage = "Please enter a valid amount greater than ₹0.")
            return
        }

        if (currentState.selectedAccountId == null) {
            _uiState.value = currentState.copy(errorMessage = "Please select an account.")
            return
        }

        if (currentState.transactionType == TransactionType.TRANSFER) {
            if (currentState.selectedDestinationAccountId == null) {
                _uiState.value = currentState.copy(errorMessage = "Please select a destination account for transfer.")
                return
            }
            if (currentState.selectedAccountId == currentState.selectedDestinationAccountId) {
                _uiState.value = currentState.copy(errorMessage = "Source and destination accounts must be different.")
                return
            }
        } else {
            if (currentState.selectedCategoryId == null) {
                _uiState.value = currentState.copy(errorMessage = "Please select a category.")
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isSaving = true)
            val entity = TransactionEntity(
                id = currentState.editingTransactionId ?: 0,
                type = currentState.transactionType,
                amountPaise = paise,
                timestamp = currentState.timestamp,
                note = currentState.note.trim(),
                accountId = currentState.selectedAccountId,
                transferDestinationAccountId = if (currentState.transactionType == TransactionType.TRANSFER) currentState.selectedDestinationAccountId else null,
                categoryId = if (currentState.transactionType == TransactionType.TRANSFER) null else currentState.selectedCategoryId,
                paymentMethod = currentState.paymentMethod,
                updatedAt = System.currentTimeMillis()
            )

            if (currentState.editingTransactionId != null) {
                repository.updateTransaction(entity)
            } else {
                repository.insertTransaction(entity)
            }
            _uiState.value = currentState.copy(isSaving = false)
            _eventFlow.emit(AddTransactionEvent.TransactionSaved)
        }
    }

    fun deleteTransaction() {
        val editingId = _uiState.value.editingTransactionId ?: return
        viewModelScope.launch {
            val existing = repository.getTransactionWithDetailsById(editingId)?.transaction
            if (existing != null) {
                repository.deleteTransaction(existing)
                _eventFlow.emit(AddTransactionEvent.TransactionDeleted)
            }
        }
    }
}
