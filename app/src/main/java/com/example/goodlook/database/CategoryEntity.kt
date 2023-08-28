package com.example.goodlook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name="listName")
    val categoryName: String


    )

{

    override fun toString(): String {
        return categoryName
    }
    constructor(categoryName: String):this(0,categoryName)

}
