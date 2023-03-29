package com.example.goodlook.database

import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Entity(tableName = "cardTable")
data class CardEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name="cardName")
    val cardName: String,
/*
    @ColumnInfo(name="type")
    val type:String,*/

    @ColumnInfo(name="deadline")
    var deadline: Long,

    @ColumnInfo(name="sysdate")
     var sysdate: Long


)

{

 // in item adapter we have a function
  fun getFormattedDeadline(): String {
     val today = Calendar.getInstance()

     val sysdate = today.timeInMillis/1000L

     val differenceMillis = deadline - sysdate

     val differenceHours = differenceMillis / (60 * 60)

     return "${differenceHours.toString()} hours"
 }




    // for add card like this

    constructor(cardName:String,deadline: Long, sysdate: Long):this(0,cardName,deadline,sysdate)
}



