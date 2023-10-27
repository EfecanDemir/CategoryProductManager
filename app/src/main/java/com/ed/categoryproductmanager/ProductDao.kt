package com.ed.sqlitecategoryexample

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product:Product)

    @Delete
    suspend fun deleteProduct(product:Product)

    @Query("SELECT * FROM product ORDER BY category ASC")
    fun getProductsByCategoryName(): Flow<List<Product>>

    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getProductsByProductName(): Flow<List<Product>>

    @Query("SELECT *, CAST(stock AS INTEGER) AS stock_int FROM product ORDER BY stock_int ASC")
    fun getProductsByProductStock(): Flow<List<Product>>

    @Query("SELECT *, CAST(price AS INTEGER) AS price_int FROM product ORDER BY price_int ASC")
    fun getProductsByProductPrice(): Flow<List<Product>>

}