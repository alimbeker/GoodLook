package com.example.goodlook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CardEntity::class], version = 4, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao():CardDao

    companion object {

        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase? {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CardDatabase::class.java,
                        "card_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return INSTANCE
            }
        }
    }
}