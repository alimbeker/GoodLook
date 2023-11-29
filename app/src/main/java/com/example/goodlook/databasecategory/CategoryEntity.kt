package com.example.goodlook.databasecategory

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "categoryName")
    val categoryName: String
) {
    override fun toString(): String {
        return categoryName
    }

    // If you want to create instances without specifying an ID
    constructor(categoryName: String) : this(UUID.randomUUID(), categoryName)
}
