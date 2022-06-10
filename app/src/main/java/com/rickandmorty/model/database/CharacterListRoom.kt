package com.rickandmorty.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rickandmorty.model.pojo.Result


@Database(entities = [Result::class],version = 1)
abstract class CharacterListRoom: RoomDatabase() {

        abstract fun getCharacterDao( ): CharacterDao

        companion object {
            @Volatile
            private var INSTANCE : CharacterListRoom? = null

            fun getDataBase(context: Context): CharacterListRoom {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(context.applicationContext,
                        CharacterListRoom::class.java,
                        "rickandmorty")
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
}