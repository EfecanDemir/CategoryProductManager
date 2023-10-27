package com.ed.sqlitecategoryexample

sealed interface ProductEvent{
    object SaveProduct:ProductEvent
    data class SetProductCategoryName(val category:String):ProductEvent
    data class SetProductName(val name:String):ProductEvent
    data class SetProductStock(val stock:String):ProductEvent
    data class SetProductPrice(val price: String):ProductEvent
    object ShowDialog:ProductEvent
    object HideDialog:ProductEvent
    data class SortProducts(val sortType: SortType):ProductEvent
    data class DeleteProduct(val product:Product):ProductEvent
}