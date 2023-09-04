package com.example.goodlook.databasecategory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): LiveData<MutableList<CategoryEntity>>

    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}