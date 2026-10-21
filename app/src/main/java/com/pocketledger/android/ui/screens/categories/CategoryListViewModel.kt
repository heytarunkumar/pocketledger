package com.pocketledger.android.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketledger.android.core.model.CategoryType
import com.pocketledger.android.data.local.entity.CategoryEntity
import com.pocketledger.android.data.repository.PocketLedgerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryListUiState(
    val selectedType: CategoryType = CategoryType.EXPENSE,
    val categories: List<CategoryEntity> = emptyList(),
    val categoryToEdit: CategoryEntity? = null,
    val showAddEditDialog: Boolean = false,
    val isLoading: Boolean = true
)

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repository: PocketLedgerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryListUiState())
    val uiState: StateFlow<CategoryListUiState> = _uiState.asStateFlow()

    init {
        loadCategories(_uiState.value.selectedType)
    }

    fun onTabSelected(type: CategoryType) {
        _uiState.value = _uiState.value.copy(selectedType = type)
        loadCategories(type)
    }

    private fun loadCategories(type: CategoryType) {
        viewModelScope.launch {
            repository.getCategoriesByType(type).collect { list ->
                _uiState.value = _uiState.value.copy(
                    categories = list,
                    isLoading = false
                )
            }
        }
    }

    fun onAddCategoryClicked() {
        _uiState.value = _uiState.value.copy(
            categoryToEdit = null,
            showAddEditDialog = true
        )
    }

    fun onEditCategoryClicked(category: CategoryEntity) {
        _uiState.value = _uiState.value.copy(
            categoryToEdit = category,
            showAddEditDialog = true
        )
    }

    fun onDismissDialog() {
        _uiState.value = _uiState.value.copy(
            showAddEditDialog = false,
            categoryToEdit = null
        )
    }

    fun saveCategory(name: String, type: CategoryType) {
        viewModelScope.launch {
            val existing = _uiState.value.categoryToEdit
            if (existing != null) {
                repository.insertCategory(
                    existing.copy(name = name, type = type)
                )
            } else {
                repository.insertCategory(
                    CategoryEntity(name = name, type = type)
                )
            }
            onDismissDialog()
        }
    }
}
