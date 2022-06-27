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
import kotlinx.coroutines.flow.Flow


class CharacterViewModel (application: Application): AndroidViewModel(application) {
        private val repository: CharacterRepository
        //desde data base
        lateinit var characterLiveDataFromDataBase : MutableLiveData<List<ResultCharacter>>
        //desde internet obvsernado paginado
       // var characterLiveDataByName : LiveData<PagingData<ResultCharacter>>


        init {
            val dao = CharacterListRoom.getDataBase(application).getCharacterDao()
            repository= CharacterRepository(dao)
            //observando desde internet live data
           // characterLiveDataByName = repository.getAllCharacters()
        }

         //corutina con flow
         fun characterPageFlow(): Flow<PagingData<ResultCharacter>> =repository.getAllCharactersFlow().cachedIn(viewModelScope)

         fun dataNextPage(page :Int ) = viewModelScope.launch {
         }

        fun searchDataByName(name :String ) = viewModelScope.launch {
            }

        fun getCharByID(id:String) : LiveData<ResultCharacter> {
            return repository.getCharacterByID(id)
        }

        fun getCharByName() : LiveData<List<ResultCharacter>> {
            return repository.getAllCharacter()
        }

    }
