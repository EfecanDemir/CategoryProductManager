package com.ed.sqlitecategoryexample

sealed interface CategoryEvent{
    object SaveCategory: CategoryEvent
    data class SetCategoryName(val categoryName: String): CategoryEvent
    object ShowDialog:CategoryEvent
    object HideDialog:CategoryEvent
    data class SortCategory(val categorySortType: CategorySortType): CategoryEvent
    data class DeleteCategory(val category: Category): CategoryEvent
}