package com.ed.sqlitecategoryexample

data class CategoryState(
    val categories: List<Category> = emptyList(),
    val categoryName: String = "",
    val isAddingCategory:Boolean=false,
    val categorySortType: CategorySortType=CategorySortType.CATEGORY_NAME
)
