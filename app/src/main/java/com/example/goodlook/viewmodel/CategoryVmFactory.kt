package com.example.goodlook.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goodlook.database.CardDao
import com.example.goodlook.databasecategory.CategoryDao

class CategoryVmFactory(
    private val dataSource: CategoryDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(dataSource,application) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }


}