package com.ed.sqlitecategoryexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModel(
    private val dao:ProductDao
):ViewModel() {
    private val _sortType= MutableStateFlow(SortType.CATEGORY)
    private val _products=_sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.CATEGORY -> dao.getProductsByCategoryName()
                SortType.NAME -> dao.getProductsByProductName()
                SortType.STOCK -> dao.getProductsByProductStock()
                SortType.PRICE -> dao.getProductsByProductPrice()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state= MutableStateFlow(ProductState())
    val state= combine(_state,_sortType,_products){state,sortType,products->
        state.copy(
            products=products,
            sortType=sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun onEvent(event: ProductEvent){
        when(event){
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    dao.deleteProduct(event.product)
                }
            }
            ProductEvent.HideDialog -> {
                _state.update { it.copy(isAddingProduct = false) }
            }
            ProductEvent.SaveProduct -> {
                val category=state.value.category
                val name=state.value.name
                val stock=state.value.stock
                val price=state.value.price

                if(category.isBlank() || name.isBlank() || stock.isBlank() || price.isBlank()){
                    return
                }
                val product=Product(
                    category =category,
                    name =name,
                    stock = stock,
                    price =price
                )
                viewModelScope.launch {
                    dao.insertProduct(product)
                }
                _state.update { it.copy(
                    isAddingProduct = false,
                    name = "",
                    category = "",
                    stock = "",
                    price = ""
                ) }
            }
            is ProductEvent.SetProductCategoryName -> {
                _state.update { it.copy(category = event.category) }
            }
            is ProductEvent.SetProductName -> {
                _state.update { it.copy(name = event.name) }
            }
            is ProductEvent.SetProductPrice -> {
                _state.update { it.copy(price = event.price) }
            }
            is ProductEvent.SetProductStock -> {
                _state.update { it.copy(stock = event.stock) }
            }
            ProductEvent.ShowDialog -> {
                _state.update { it.copy(isAddingProduct = true) }
            }
            is ProductEvent.SortProducts -> {
                _sortType.value=event.sortType
            }
        }
    }
}