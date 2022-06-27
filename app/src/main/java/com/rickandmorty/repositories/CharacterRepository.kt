package com.rickandmorty.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rickandmorty.model.pojo.ResultCharacter
import com.rickandmorty.model.database.CharacterDao
import com.rickandmorty.pagingsource.RickyMortyPagingSource
import com.rickandmorty.remote.Retrofit
import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val dao: CharacterDao) {

    private val services = Retrofit.getRetrofitInstance()
    val liveDataCharactersDB : LiveData<List<ResultCharacter>> = dao.getAllCharactersDataBase()
    val liveDataCharResponse = MutableLiveData<List<ResultCharacter>>()


    //paginado con liveData
    fun getAllCharacters()=
        Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { RickyMortyPagingSource(services) }
        ).liveData

    //paginado con flow
      fun getAllCharactersFlow():Flow<PagingData<ResultCharacter>> {
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { RickyMortyPagingSource(services) }
        ).flow}

        fun getCharacterByID(id: String): LiveData<ResultCharacter> {
            return dao.getCharacterByID(id)

        }

        fun getAllCharacter(): LiveData<List<ResultCharacter>> {
            return dao.getAllCharactersDataBase()

        }
    }