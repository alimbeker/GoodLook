package com.example.goodlook.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CardRepository(private val cardDao: CardDao) {

    val allCards: LiveData<MutableList<CardEntity>> = cardDao.getAll()

    val groceries : LiveData<MutableList<CardEntity>> = cardDao.getGroceries()

    val work : LiveData<MutableList<CardEntity>> = cardDao.getWork()

    val personal : LiveData<MutableList<CardEntity>> = cardDao.getPersonal()



    suspend fun insert(card: CardEntity) {
        cardDao.insert(card)
    }

    suspend fun delete(card: CardEntity) = withContext(Dispatchers.IO) {
        delay(500)
        cardDao.delete(card)
    }

    suspend fun clear() {
        cardDao.clear()
    }

    fun getAll(): LiveData<MutableList<CardEntity>> {
        return cardDao.getAll()
    }





    suspend fun deleteByDeadline(currentTime: Long) {
        cardDao.deleteByDeadline(currentTime)
    }







}