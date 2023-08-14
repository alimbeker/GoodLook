package com.example.goodlook.database

import androidx.lifecycle.LiveData
import androidx.room.Insert

class CardRepository(private val cardDao: CardDao) {

    val allCards: LiveData<MutableList<CardEntity>> = cardDao.getAll()

    val sortedCards : LiveData<MutableList<CardEntity>> = cardDao.sortByDate()

    val favorCards : LiveData<MutableList<CardEntity>> = cardDao.favorCards()



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

    fun favorCards(): LiveData<MutableList<CardEntity>> {
        return cardDao.favorCards()
    }

    suspend fun deleteByDeadline(currentTime: Long) {
        cardDao.deleteByDeadline(currentTime)
    }







}