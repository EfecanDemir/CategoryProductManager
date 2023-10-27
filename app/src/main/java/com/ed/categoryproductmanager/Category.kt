package com.ed.sqlitecategoryexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    val categoryName: String,
    @PrimaryKey(autoGenerate=true)
    val id:Int=0
)