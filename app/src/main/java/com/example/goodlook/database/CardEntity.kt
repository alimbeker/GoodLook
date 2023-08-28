package com.example.goodlook.database

import android.text.format.DateFormat
import androidx.room.*
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

    @ColumnInfo(name="deadline")
    var deadline: Long,

    @ColumnInfo(name="sysdate")
     var sysdate: Long,

    @ColumnInfo(name="category")
    val cardCategory: String




)

{

 // in item adapter we have a function
 fun getFormattedDeadline(): String {
     val differenceMillis = deadline - Calendar.getInstance().timeInMillis / 1000L

     val differenceDays = differenceMillis / (60 * 60 * 24)
     val remainingSeconds = differenceMillis % (60 * 60 * 24)
     val differenceHours = remainingSeconds / (60 * 60)

     return if (differenceDays > 0) {
         "$differenceDays days $differenceHours hours"
     } else {
         "$differenceHours hours"
     }
 }





    // for add card like this

    constructor(cardName:String,deadline: Long, sysdate: Long,cardCategory: String):this(0,cardName,deadline,sysdate,cardCategory)
}



