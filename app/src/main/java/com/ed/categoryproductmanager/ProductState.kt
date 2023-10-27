package com.ed.sqlitecategoryexample

data class ProductState(
    val products: List<Product> = emptyList(),
    val category:String="",
    val name: String="",
    val stock: String="",
    val price: String="",
    val isAddingProduct:Boolean=false,
    val sortType: SortType=SortType.CATEGORY
)
