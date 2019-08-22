package com.nopal.awokowokwok.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nopal.awokowokwok.mapren

@Database(entities = [mapren::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun MyFriendDao() : MyFriendDao
    companion object {
        var INSTANCE: AppDatabase? = null
        fun getAppDataBase (context: Context): AppDatabase?{
            if (INSTANCE == null) {
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "MyFriendAppDB").build()
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}