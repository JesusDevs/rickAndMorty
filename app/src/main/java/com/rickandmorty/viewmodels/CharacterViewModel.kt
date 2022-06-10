package com.rickandmorty.viewmodels

import android.app.Application
import android.text.Editable
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
         var characterLiveDataFromDataBase : LiveData<List<Result>>
        //desde internet name
        var characterLiveDataByName : LiveData<List<Result>>




        init {
            val dao = CharacterListRoom.getDataBase(application).getCharacterDao()
            repository= CharacterRepository(dao)

            viewModelScope.launch {
                repository.getCharactersWithCoroutines(page = 1)
            }
            characterLiveDataByName = repository.liveDataCharResponse
        characterLiveDataFromDataBase = repository.liveDataCharactersDB
        }

         //corutina para buscar personajes por nombre
        fun searchDataByName(name: Editable) = viewModelScope.launch {
        repository.searchCharactersWithCoroutines(name)
        }

         fun dataNextPage(page :Int ) = viewModelScope.launch {
             repository.getCharactersWithCoroutines(page)
         }


        fun getCharByID(id:String) : LiveData<Result> {
            return repository.getCharacterByID(id)
        }

        fun getCharByName() : LiveData<List<Result>> {
            return repository.getAllCharacter()
        }

    }
