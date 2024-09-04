package com.example.contactlistexercise.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactlistexercise.database.dao.ContactDao
import com.example.contactlistexercise.database.model.ContactDb

@Database(
    entities = [ContactDb::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getContactDao() : ContactDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Contact_App"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

}