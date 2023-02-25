package com.example.goodlook.database

import android.widget.ImageView

data class CardEntity(
    val id: Int,
    val cardName: String,
   /* val image:ImageView,*/
    val deadline: Int,
    val text: String
)