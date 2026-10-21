package com.pocketledger.android.ui.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.core.util.CsvExporter
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.TransactionWithDetails
import com.pocketledger.android.data.repository.PocketLedgerRepository
import com.pocketledger.android.domain.CategoryPercentageModel
import com.pocketledger.android.domain.FinancialCalculator
import com.pocketledger.android.domain.MonthlyTrendModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

enum class ReportPeriod(val label: String) {
    THIS_MONTH("This Month"),
    LAST_MONTH("Last Month"),
    THIS_YEAR("This Year"),
    ALL_TIME("All Time")
}

data class ReportsUiState(
    val selectedPeriod: ReportPeriod = ReportPeriod.THIS_MONTH,
    val totalIncomePaise: Long = 0L,
    val totalExpensePaise: Long = 0L,
    val netCashFlowPaise: Long = 0L,
    val savingsRatePercentage: Double = 0.0,
    val categoryPercentages: List<CategoryPercentageModel> = emptyList(),
    val monthlyTrends: List<MonthlyTrendModel> = emptyList(),
    val allTransactionsWithDetails: List<TransactionWithDetails> = emptyList(),
    val exportedCsvContent: String? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: PocketLedgerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadReportData()
    }

    fun onPeriodSelected(period: ReportPeriod) {
        _uiState.value = _uiState.value.copy(selectedPeriod = period)
        loadReportData()
    }

    private fun loadReportData() {
        viewModelScope.launch {
            combine(
                repository.getAllTransactionsWithDetails(),
                repository.getAllActiveCategories()
            ) { allTxWithDetails, categories ->
                val allTx = allTxWithDetails.map { it.transaction }
                val period = _uiState.value.selectedPeriod

                // Filter transactions by period
                val (startTime, endTime) = calculatePeriodRange(period)
                val filteredTx = if (startTime != null && endTime != null) {
                    allTx.filter { it.timestamp in startTime..endTime }
                } else {
                    allTx
                }

                val income = FinancialCalculator.calculateTotalIncome(filteredTx)
                val expense = FinancialCalculator.calculateTotalExpense(filteredTx)
                val netCashFlow = FinancialCalculator.calculateNetCashFlow(filteredTx)
                val savingsRate = FinancialCalculator.calculateSavingsRate(filteredTx)
                val categoryPercentages = FinancialCalculator.calculateCategoryPercentages(filteredTx, categories)
                val monthlyTrends = FinancialCalculator.calculateMonthlyTrend(allTx, monthsCount = 6)

                _uiState.value.copy(
                    totalIncomePaise = income,
                    totalExpensePaise = expense,
                    netCashFlowPaise = netCashFlow,
                    savingsRatePercentage = savingsRate,
                    categoryPercentages = categoryPercentages,
                    monthlyTrends = monthlyTrends,
                    allTransactionsWithDetails = allTxWithDetails,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun exportTransactionsToCsv() {
        val transactions = _uiState.value.allTransactionsWithDetails
        val csv = CsvExporter.exportTransactionsToCsv(transactions)
        _uiState.value = _uiState.value.copy(exportedCsvContent = csv)
    }

    fun clearExportedCsv() {
        _uiState.value = _uiState.value.copy(exportedCsvContent = null)
    }

    private fun calculatePeriodRange(period: ReportPeriod): Pair<Long?, Long?> {
        val calendar = Calendar.getInstance()
        return when (period) {
            ReportPeriod.THIS_MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val start = calendar.timeInMillis

                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
                val end = calendar.timeInMillis

                Pair(start, end)
            }
            ReportPeriod.LAST_MONTH -> {
                calendar.add(Calendar.MONTH, -1)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val start = calendar.timeInMillis

                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
                val end = calendar.timeInMillis

                Pair(start, end)
            }
            ReportPeriod.THIS_YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val start = calendar.timeInMillis

                calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
                val end = calendar.timeInMillis

                Pair(start, end)
            }
            ReportPeriod.ALL_TIME -> Pair(null, null)
        }
    }
}
