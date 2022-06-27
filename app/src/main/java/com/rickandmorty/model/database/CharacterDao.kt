package com.rickandmorty.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rickandmorty.model.pojo.ResultCharacter


@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(list: List<ResultCharacter>)

    @Query("SELECT * FROM all_character_table order by name asc")
    fun getAllCharactersDataBase(): LiveData<List<ResultCharacter>>

    @Query("SELECT * FROM all_character_table WHERE id = :id")
    fun getCharacterByID(id: String): LiveData<ResultCharacter>

}