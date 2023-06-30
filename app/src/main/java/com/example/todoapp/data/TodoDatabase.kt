package com.example.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Todo::class)], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao


    companion object {
        /*The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
       This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
       It means that changes made by one thread to INSTANCE are visible to all other threads immediately.*/
        @Volatile
        private var INSTANCE : TodoDatabase? = null
        fun getInstance(context: Context) : TodoDatabase {
            // only one thread of execution at a time can enter this block of code
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                "todo_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}