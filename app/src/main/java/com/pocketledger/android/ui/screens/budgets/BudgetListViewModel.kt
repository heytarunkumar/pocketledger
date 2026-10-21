package com.pocketledger.android.ui.screens.budgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.core.model.TransactionType
import com.pocketledger.android.data.local.entity.BudgetEntity
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.local.entity.SavingsGoalEntity
import com.pocketledger.android.data.repository.PocketLedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class CategoryBudgetUiModel(
    val budget: BudgetEntity,
    val category: CategoryEntity,
    val spentAmountPaise: Long,
    val utilizationPercentage: Float,
    val isOverspent: Boolean
)

data class BudgetListUiState(
    val currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH) + 1,
    val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val categoryBudgets: List<CategoryBudgetUiModel> = emptyList(),
    val expenseCategories: List<CategoryEntity> = emptyList(),
    val savingsGoals: List<SavingsGoalEntity> = emptyList(),
    val budgetToEdit: BudgetEntity? = null,
    val goalToEdit: SavingsGoalEntity? = null,
    val showAddEditBudgetDialog: Boolean = false,
    val showAddEditGoalDialog: Boolean = false,
    val isLoading: Boolean = true
)

@HiltViewModel
class BudgetListViewModel @Inject constructor(
    private val repository: PocketLedgerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetListUiState())
    val uiState: StateFlow<BudgetListUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        val month = _uiState.value.currentMonth
        val year = _uiState.value.currentYear

        viewModelScope.launch {
            combine(
                repository.getBudgetsForMonth(month, year),
                repository.getCategoriesByType(CategoryType.EXPENSE),
                repository.getAllTransactions(),
                repository.getAllSavingsGoals()
            ) { budgets, categories, transactions, goals ->
                val categoryMap = categories.associateBy { it.id }

                // Calculate month boundary
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month - 1)
                    set(Calendar.DAY_OF_MONTH, 1)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val startOfMonth = calendar.timeInMillis
                calendar.add(Calendar.MONTH, 1)
                val endOfMonth = calendar.timeInMillis - 1

                val monthExpenses = transactions.filter {
                    it.type == TransactionType.EXPENSE &&
                            it.timestamp in startOfMonth..endOfMonth
                }

                val budgetUiModels = budgets.mapNotNull { b ->
                    val cat = categoryMap[b.categoryId] ?: return@mapNotNull null
                    val spent = monthExpenses
                        .filter { it.categoryId == b.categoryId }
                        .sumOf { it.amountPaise }

                    val percent = if (b.amountPaise > 0) (spent.toFloat() / b.amountPaise.toFloat()) * 100f else 0f
                    CategoryBudgetUiModel(
                        budget = b,
                        category = cat,
                        spentAmountPaise = spent,
                        utilizationPercentage = percent,
                        isOverspent = spent > b.amountPaise
                    )
                }

                _uiState.value.copy(
                    categoryBudgets = budgetUiModels,
                    expenseCategories = categories,
                    savingsGoals = goals,
                    isLoading = false
                )
            }.collect { updated ->
                _uiState.value = updated
            }
        }
    }

    fun onAddBudgetClicked() {
        _uiState.value = _uiState.value.copy(
            budgetToEdit = null,
            showAddEditBudgetDialog = true
        )
    }

    fun onEditBudgetClicked(budget: BudgetEntity) {
        _uiState.value = _uiState.value.copy(
            budgetToEdit = budget,
            showAddEditBudgetDialog = true
        )
    }

    fun onDismissBudgetDialog() {
        _uiState.value = _uiState.value.copy(
            showAddEditBudgetDialog = false,
            budgetToEdit = null
        )
    }

    fun saveBudget(categoryId: Long, amountPaise: Long) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val existing = currentState.budgetToEdit
            if (existing != null) {
                repository.updateBudget(
                    existing.copy(
                        categoryId = categoryId,
                        amountPaise = amountPaise
                    )
                )
            } else {
                repository.insertBudget(
                    BudgetEntity(
                        categoryId = categoryId,
                        amountPaise = amountPaise,
                        month = currentState.currentMonth,
                        year = currentState.currentYear
                    )
                )
            }
            onDismissBudgetDialog()
        }
    }

    fun deleteBudget(budget: BudgetEntity) {
        viewModelScope.launch {
            repository.deleteBudget(budget)
        }
    }

    // Savings Goals
    fun onAddGoalClicked() {
        _uiState.value = _uiState.value.copy(
            goalToEdit = null,
            showAddEditGoalDialog = true
        )
    }

    fun onEditGoalClicked(goal: SavingsGoalEntity) {
        _uiState.value = _uiState.value.copy(
            goalToEdit = goal,
            showAddEditGoalDialog = true
        )
    }

    fun onDismissGoalDialog() {
        _uiState.value = _uiState.value.copy(
            showAddEditGoalDialog = false,
            goalToEdit = null
        )
    }

    fun saveSavingsGoal(title: String, targetPaise: Long, currentSavedPaise: Long) {
        viewModelScope.launch {
            val existing = _uiState.value.goalToEdit
            if (existing != null) {
                repository.updateSavingsGoal(
                    existing.copy(
                        title = title,
                        targetAmountPaise = targetPaise,
                        currentSavedPaise = currentSavedPaise
                    )
                )
            } else {
                repository.insertSavingsGoal(
                    SavingsGoalEntity(
                        title = title,
                        targetAmountPaise = targetPaise,
                        currentSavedPaise = currentSavedPaise
                    )
                )
            }
            onDismissGoalDialog()
        }
    }

    fun deleteSavingsGoal(goal: SavingsGoalEntity) {
        viewModelScope.launch {
            repository.deleteSavingsGoal(goal)
        }
    }
}
