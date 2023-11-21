package com.example.goodlook.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goodlook.database.CardDao
import com.example.goodlook.databasecategory.CategoryDao


class VmFactory<T : ViewModel>(
    private val dataSource: Any, // Use 'Any' to handle both CardDao and CategoryDao
    private val application: Application,
    private val viewModelClass: Class<T>
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            // Cast 'dataSource' based on the ViewModel type
            return when (viewModelClass) {
                FavorFragmentViewModel::class.java -> FavorFragmentViewModel(dataSource as CardDao, application) as T
                CategoryViewModel::class.java -> CategoryViewModel(dataSource as CategoryDao, application) as T
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
