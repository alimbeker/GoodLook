package com.example.goodlook.database

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

    @ColumnInfo(name = "cardList")
    val cardList : LiveData<MutableList<CardEntity>>


    )

{

    override fun toString(): String {
        return categoryName
    }
    constructor(categoryName: String, cardList: LiveData<MutableList<CardEntity>>):this(0,categoryName,cardList)

}
