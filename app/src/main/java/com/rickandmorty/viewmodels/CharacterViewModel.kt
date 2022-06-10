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
        val characterLiveDataFromDataBase : LiveData<List<Result>>

        init {

            val dao = CharacterListRoom.getDataBase(application).getCharacterDao()
            repository= CharacterRepository(dao)

            viewModelScope.launch {
                repository.getCharactersWithCoroutines(page = 1)
            }

            characterLiveDataFromDataBase = repository.liveDataCharactersDB
        }
        fun getGameByID(id:String) : LiveData<Result> {
            return repository.getCharacterByID(id)
        }

        fun getGameByPlatform() : LiveData<List<Result>> {
            return repository.getAllCharacter()
        }

    }
