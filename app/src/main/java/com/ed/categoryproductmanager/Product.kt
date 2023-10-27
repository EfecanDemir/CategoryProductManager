package com.ed.sqlitecategoryexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    val category: String,
    val name: String,
    val stock: String,
    val price: String,
    @PrimaryKey(autoGenerate=true)
    val id:Int=0
)
