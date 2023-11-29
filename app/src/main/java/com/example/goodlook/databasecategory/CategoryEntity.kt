package com.example.goodlook.databasecategory

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "category", indices = [Index(value = ["categoryName"], unique = true)])
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "categoryName")
    val categoryName: String
) {
    override fun toString(): String {
        return categoryName
    }

    // If you want to create instances without specifying an ID
    constructor(categoryName: String) : this(0, categoryName)
}