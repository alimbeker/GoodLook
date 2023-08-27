package com.example.goodlook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name="listName")
    val categoryName: String,

    @ColumnInfo(name="cardList")
    val cards: List<CardEntity>
    )
{
    constructor(categoryName: String,cards: List<CardEntity>):this(0,categoryName,cards)

}
