package com.example.goodlook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.goodlook.databasecategory.CategoryEntity

class CategoryViewModel : ViewModel() {

    private val _categoryList = MutableLiveData<List<CategoryEntity>>()
    val categoryList: LiveData<List<CategoryEntity>> = _categoryList

    init {
        _categoryList.value = getCategoryObjects()
    }

    private fun getCategoryObjects(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(0, "Groceries"),
            CategoryEntity(1, "Work"),
            CategoryEntity(2, "Personal")
        )
    }

    fun insertCategory(category: CategoryEntity) {
        val currentList = _categoryList.value.orEmpty().toMutableList()
        currentList.add(category)
        _categoryList.value = currentList
    }

    fun deleteCategory(category: CategoryEntity) {
        val currentList = _categoryList.value.orEmpty().toMutableList()
        currentList.remove(category)
        _categoryList.value = currentList
    }
}
