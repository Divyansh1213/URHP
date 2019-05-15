package com.example.urhp

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

@Database(entities = [Person::class], version = 1)
abstract  class PersonDatabase : RoomDatabase() {
    abstract fun personDao() : PersonDao

    companion object {
        @Volatile private var INSTANCE: PersonDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): PersonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDatabase::class.java,
                    "Person_database"
                ).allowMainThreadQueries().build()
                return INSTANCE!!
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}