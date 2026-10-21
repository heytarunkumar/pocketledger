package com.pocketledger.android.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.core.security.SecurityPreferences
import com.pocketledger.android.core.util.CsvExporter
import com.pocketledger.android.data.repository.PocketLedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val isBiometricLockEnabled: Boolean = false,
    val exportedCsvContent: String? = null,
    val showClearDataDialog: Boolean = false,
    val isClearingData: Boolean = false
)

sealed interface SettingsEvent {
    data object DataCleared : SettingsEvent
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val securityPreferences: SecurityPreferences,
    private val repository: PocketLedgerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<SettingsEvent>()
    val eventFlow: SharedFlow<SettingsEvent> = _eventFlow.asSharedFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        _uiState.value = _uiState.value.copy(
            isBiometricLockEnabled = securityPreferences.isBiometricLockEnabled()
        )
    }

    fun onBiometricLockToggled(enabled: Boolean) {
        securityPreferences.setBiometricLockEnabled(enabled)
        _uiState.value = _uiState.value.copy(isBiometricLockEnabled = enabled)
    }

    fun exportTransactionsCsv() {
        viewModelScope.launch {
            val transactions = repository.getAllTransactionsWithDetails().first()
            val csv = CsvExporter.exportTransactionsToCsv(transactions)
            _uiState.value = _uiState.value.copy(exportedCsvContent = csv)
        }
    }

    fun clearExportedCsv() {
        _uiState.value = _uiState.value.copy(exportedCsvContent = null)
    }

    fun onShowClearDataDialog() {
        _uiState.value = _uiState.value.copy(showClearDataDialog = true)
    }

    fun onDismissClearDataDialog() {
        _uiState.value = _uiState.value.copy(showClearDataDialog = false)
    }

    fun clearAllData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isClearingData = true)

            // Delete all transactions
            val transactions = repository.getAllTransactions().first()
            transactions.forEach { tx ->
                repository.deleteTransaction(tx)
            }

            // Clear preferences
            securityPreferences.clearAllPreferences()

            _uiState.value = _uiState.value.copy(
                isClearingData = false,
                showClearDataDialog = false,
                isBiometricLockEnabled = false
            )

            _eventFlow.emit(SettingsEvent.DataCleared)
        }
    }
}
