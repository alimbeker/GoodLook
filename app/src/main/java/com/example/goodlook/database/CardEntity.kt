package com.example.goodlook.database

import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cardTable")
data class CardEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name="cardName")
    val cardName: String,
   /* val image:ImageView,*/

    @ColumnInfo(name="deadline")
    var deadline: Int


)

{
    // for add card like this

    constructor(cardName:String,deadline: Int):this(0,cardName,deadline)
}

