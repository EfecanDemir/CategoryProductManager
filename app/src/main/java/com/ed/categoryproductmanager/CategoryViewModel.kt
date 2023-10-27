package com.ed.sqlitecategoryexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryViewModel(
    private val dao:CategoryDao
): ViewModel() {
    private val _categorySortType= MutableStateFlow(CategorySortType.CATEGORY_NAME)
    private val _categories=_categorySortType
        .flatMapLatest { categorySortType ->
            when(categorySortType){
                CategorySortType.CATEGORY_NAME -> dao.getCategoryByCategoryName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state= MutableStateFlow(CategoryState())
    val state= combine(_state,_categorySortType,_categories){state,categorySortType,categories->
        state.copy(
            categories=categories,
            categorySortType=categorySortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CategoryState())

    fun onEvent(event: CategoryEvent){
        when(event) {
            is CategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    dao.deleteCategory(event.category)
                }
            }
            CategoryEvent.HideDialog -> {
                _state.update { it.copy(isAddingCategory = false) }
            }
            CategoryEvent.SaveCategory -> {
                val categoryName=state.value.categoryName

                if(categoryName.isBlank()){
                    return
                }
                val category=Category(
                    categoryName=categoryName
                )
                viewModelScope.launch {
                    dao.insertCategory(category)
                }
                _state.update { it.copy(
                    isAddingCategory = false,
                    categoryName="",
                ) }
            }
            is CategoryEvent.SetCategoryName -> {
                _state.update { it.copy(
                    categoryName = event.categoryName
                ) }
            }
            CategoryEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingCategory = true
                ) }
            }
            is CategoryEvent.SortCategory -> {
                _categorySortType.value=event.categorySortType
            }
        }
    }
}