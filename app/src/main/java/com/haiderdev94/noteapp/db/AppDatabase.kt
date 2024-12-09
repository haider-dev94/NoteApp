package com.haiderdev94.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.haiderdev94.noteapp.model.Note

@Database(version = 1, entities = [Note::class], exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getNoteDao():NoteDao
    companion object{
        @Volatile
        private var INSTANCE:AppDatabase?=null
        fun getDatabase(context: Context):AppDatabase{
            return INSTANCE?: synchronized(this){
                val mInstance= Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "NoteDatabase").build()
                INSTANCE=mInstance
                return mInstance
            }
        }
    }
}