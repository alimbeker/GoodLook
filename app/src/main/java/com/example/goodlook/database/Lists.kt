package com.example.goodlook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "lists")
data class Lists(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name="listName")
    val name: String,

    @ColumnInfo(name="cardList")
    val cards: List<CardEntity>
    )
{

}
