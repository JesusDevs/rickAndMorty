package com.rickandmorty.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.rickandmorty.model.pojo.ResultCharacter
import com.rickandmorty.model.database.CharacterDao
import com.rickandmorty.pagingsource.RickyMortyPagingSource
import com.rickandmorty.remote.Retrofit

class CharacterRepository(private val dao: CharacterDao) {

    private val services = Retrofit.getRetrofitInstance()
    val liveDataCharactersDB : LiveData<List<ResultCharacter>> = dao.getAllCharactersDataBase()
    val liveDataCharResponse = MutableLiveData<List<ResultCharacter>>()

   /* suspend fun getCharactersWithCoroutines( page :Int)  {
        try {
            val response = Retrofit.getRetrofitInstance().getAllCharacter(page )

            when(response.isSuccessful) {

                true -> response.body()?.let {

                    //inserto los personajes en la base de datos
                    dao.insertAllCharacter(it.results)
                    Log.d("repoCharacter", "${it.results}")
                }
                false -> Log.d("ERROR", " ${response.code()} : ${response.errorBody()} ")
            }
        } catch (t: Throwable){
            Log.e("ERROR CORUTINA", t.message.toString())
        }
    }*/


    fun getAllCharacters()=
        Pager(config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = { RickyMortyPagingSource(services) }
        ).liveData

    suspend fun searchCharactersWithCoroutines( name :String)  {
        try {
            val response = Retrofit.getRetrofitInstance().searchAllCharacter(name)

            when(response.isSuccessful) {

                true -> response.body()?.let {
                    liveDataCharResponse.value=it.results
                   // debo observar lo que llega
                    Log.d("repoCharacter", "${it.results}")
                }
                false -> Log.d("ERROR", " ${response.code()} : ${response.errorBody()} ")
            }
        } catch (t: Throwable){
            Log.e("ERROR CORUTINA", t.message.toString())
        }
    }



  fun getCharacterByID(id: String) : LiveData<ResultCharacter> {
       return dao.getCharacterByID(id)

  }
    fun getAllCharacter() : LiveData<List<ResultCharacter> >{
        return dao.getAllCharactersDataBase()

    }

}