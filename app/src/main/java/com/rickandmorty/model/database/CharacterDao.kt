package com.rickandmorty.model.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.rickandmorty.model.pojo.CharacterResponse

interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(list: List<CharacterResponse>)

}