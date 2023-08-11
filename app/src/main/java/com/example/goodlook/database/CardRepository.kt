package com.example.goodlook.database

import androidx.lifecycle.LiveData
import androidx.room.Insert

class CardRepository(private val cardDao: CardDao) {

    val allCards: LiveData<MutableList<CardEntity>> = cardDao.getAll()


    suspend fun insert(card: CardEntity) {
        cardDao.insert(card)
    }

    suspend fun delete(card: CardEntity) {
        cardDao.delete(card)
    }

    suspend fun clear() {
        cardDao.clear()
    }

    fun getAll(): LiveData<MutableList<CardEntity>> {
        return cardDao.getAll()
    }

    fun sortByDate(): LiveData<MutableList<CardEntity>> {
        return cardDao.sortByDate()
    }


    suspend fun deleteByDeadline(currentTime: Long) {
        cardDao.deleteByDeadline(currentTime)
    }







}