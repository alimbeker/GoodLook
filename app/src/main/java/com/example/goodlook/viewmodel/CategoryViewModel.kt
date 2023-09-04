package com.example.goodlook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.goodlook.database.CardDao
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.database.CardEntity
import com.example.goodlook.database.CardRepository
import com.example.goodlook.databasecategory.CategoryDao
import com.example.goodlook.databasecategory.CategoryEntity
import com.example.goodlook.databasecategory.CategoryRepository

class CategoryViewModel(val database: CategoryDao, application: Application) : AndroidViewModel(application) {

    val allCards : LiveData<MutableList<CardEntity>>



    val repository : CardRepository


    init {
        val dao = CardDatabase.getInstance(application)!!.cardDao()
        repository = CardRepository(dao)
        allCards =  repository.allCards


    }



    private fun getCategoryObjects(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(0, "Groceries"),
            CategoryEntity(1, "Work"),
            CategoryEntity(2, "Personal")
        )
    }

    fun insertCategory(category: CategoryEntity) {

    }

    fun deleteCategory(category: CategoryEntity) {

    }
}
