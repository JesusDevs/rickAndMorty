package com.rickandmorty.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rickandmorty.model.database.CharacterListRoom
import com.rickandmorty.repositories.CharacterRepository
import kotlinx.coroutines.launch
import com.rickandmorty.model.pojo.Result



class CharacterViewModel (application: Application): AndroidViewModel(application) {
        private val repository: CharacterRepository
        //desde data base
        val characterLiveDataFromDataBase : LiveData<List<Result>>
        //desde internet
       val allCharacterDatafromNet : LiveData<List<Result>>

        init {

            val dao = CharacterListRoom.getDataBase(application).getCharacterDao()
            repository= CharacterRepository(dao)

            viewModelScope.launch {
                repository.getCharactersWithCoroutines(page = 1)
            }

            allCharacterDatafromNet=repository.liveDataCharResponse
            characterLiveDataFromDataBase = repository.liveDataCharactersDB
        }
         //corutina para buscar personajes por nombre
        fun searchDataByName(name :String ) = viewModelScope.launch {
        repository.searchCharactersWithCoroutines(name)
        }
        fun getCharByID(id:String) : LiveData<Result> {
            return repository.getCharacterByID(id)
        }

        fun getCharByName() : LiveData<List<Result>> {
            return repository.getAllCharacter()
        }

    }