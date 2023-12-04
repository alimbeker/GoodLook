package com.example.goodlook.viewmodel

import android.app.Application
import androidx.lifecycle.*
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
    private val dao = CategoryDatabase.getInstance(application)!!.categoryDao()
    private val repository = CategoryRepository(dao)
    var allCategories : LiveData<MutableList<CategoryEntity>> =  repository.allCategories


   init {
       val dao = CategoryDatabase.getInstance(application)!!.categoryDao()
       allCategories =  repository.allCategories
   }


    fun onInsertCategory(categoryName:String) {
        viewModelScope.launch {
            insertCategory(categoryName)
        }
    }

    fun onDeleteCategory(categoryName: CategoryEntity) {
        viewModelScope.launch {
            deleteCategory(categoryName)
        }
    }

    suspend fun insertCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(CategoryEntity(categoryName))
        }
    }

    suspend fun deleteCategory(categoryName: CategoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(categoryName)
        }
    }


}
