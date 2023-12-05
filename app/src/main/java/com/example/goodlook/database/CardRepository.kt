package com.example.goodlook.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

class CardRepository(private val cardDao: CardDao) {

    val allCards: LiveData<MutableList<CardEntity>> = cardDao.getAll()





    suspend fun insert(card: CardEntity) {
        cardDao.insert(card)
    }

    suspend fun delete(card: CardEntity) = withContext(Dispatchers.IO) {
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

    suspend fun getByCategoryId(category_id: UUID): List<CardEntity> {
        return cardDao.getByCategoryId(category_id)
    }









}