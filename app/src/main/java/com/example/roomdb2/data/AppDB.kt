package com.example.roomdb2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun studentDao() : StudentDao

    companion object{
        @Volatile
        private var INSTANCE: AppDB ?= null
        fun getDB(context: Context): AppDB{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance

            }
        }
    }
}