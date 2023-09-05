package com.example.goodlook.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.goodlook.database.CardDao
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.database.CardEntity
import com.example.goodlook.database.CardRepository
import com.example.goodlook.databasecategory.CategoryDao
import com.example.goodlook.databasecategory.CategoryDatabase
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databasecategory.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(val database: CategoryDao, application: Application) : AndroidViewModel(application) {

    val allCards : LiveData<MutableList<CategoryEntity>>



    val repository: CategoryRepository


    init {
        val dao = CategoryDatabase.getInstance(application)!!.categoryDao()
        repository = CategoryRepository(dao)
        allCards =  repository.allCategories


    }



    private fun getCategoryObjects(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(0, "Groceries"),
            CategoryEntity(1, "Work"),
            CategoryEntity(2, "Personal")
        )
    }
    fun onInsertCategory(categoryName:String) {
        viewModelScope.launch {
            insertCategory(categoryName)
        }
    }
    suspend fun insertCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(CategoryEntity(categoryName))
        }
    }

    suspend fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(category)
        }
    }
}
