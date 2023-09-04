package com.example.goodlook.databasecategory

import androidx.lifecycle.LiveData

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<MutableList<CategoryEntity>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: CategoryEntity) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategory(category: CategoryEntity) {
        categoryDao.deleteCategory(category)
    }
}