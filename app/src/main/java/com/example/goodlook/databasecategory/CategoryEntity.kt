package com.example.goodlook.databasecategory

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name="listName")
    val categoryName: String,




    )

{

    override fun toString(): String {
        return categoryName
    }
    constructor(categoryName: String):this(0,categoryName)

}
