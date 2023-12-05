package com.example.goodlook.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import java.util.UUID

@Dao
interface CardDao {

    //insert new CardEntity
    @Insert
    suspend fun insert(card: CardEntity)

    @Query("DELETE FROM cardTable")
    suspend fun clear()


    @Query("Select * from cardTable")
    fun getAll(): LiveData<MutableList<CardEntity>>



    @Delete
    suspend fun delete(card: CardEntity)


    @Query("DELETE FROM cardTable WHERE deadline < :currentTime")
    suspend fun deleteByDeadline(currentTime: Long)


    @Query("SELECT * FROM cardTable WHERE category = :category_id")
    suspend fun getByCategoryId(category_id: UUID): List<CardEntity>


}