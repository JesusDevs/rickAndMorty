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
        //desde internet name
       val allCharacterDatafromNet : LiveData<List<Result>>
       //desde internet
        val allCharacterDataPage: LiveData<List<Result>>



        init {
            val dao = CharacterListRoom.getDataBase(application).getCharacterDao()
            repository= CharacterRepository(dao)
            viewModelScope.launch {
                repository.getCharactersWithCoroutines(page(0))
            }

            allCharacterDatafromNet=repository.liveDataCharResponse
            allCharacterDataPage = repository.liveDataCharPage
            characterLiveDataFromDataBase = repository.liveDataCharactersDB
        }
        fun page (page:Int) : Int{
            page+1
            return page
        }
         //corutina para buscar personajes por nombre
        fun searchDataByName(name :String ) = viewModelScope.launch {
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
