package com.ed.sqlitecategoryexample

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Category::class],
    version = 1
)
abstract class CategoryDatabase: RoomDatabase(){

    abstract val dao: CategoryDao
}
