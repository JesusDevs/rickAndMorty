package com.rickandmorty.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.rickandmorty.model.database.CharacterListRoom
import com.rickandmorty.repositories.CharacterRepository
import kotlinx.coroutines.launch
import com.rickandmorty.model.pojo.ResultCharacter
import kotlinx.coroutines.InternalCoroutinesApi



@SuppressLint("CheckResult")
class CharacterViewModel (application: Application): AndroidViewModel(application) {
        private val repository: CharacterRepository
        //desde data base
        lateinit var characterLiveDataFromDataBase : MutableLiveData<List<ResultCharacter>>
        //desde internet name
        var characterLiveDataByName : LiveData<PagingData<ResultCharacter>>


        init {
            val dao = CharacterListRoom.getDataBase(application).getCharacterDao()
            repository= CharacterRepository(dao)

            characterLiveDataByName = repository.getAllCharacters()

        }

         //corutina para buscar personajes por nombre

         fun dataNextPage(page :Int ) = viewModelScope.launch {
           //  repository.getCharactersWithCoroutines(page)
         }

        fun searchDataByName(name :String ) = viewModelScope.launch {
            repository.searchCharactersWithCoroutines(name)}
        fun getCharByID(id:String) : LiveData<ResultCharacter> {
            return repository.getCharacterByID(id)
        }

        fun getCharByName() : LiveData<List<ResultCharacter>> {
            return repository.getAllCharacter()
        }

    }
