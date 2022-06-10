package com.rickandmorty.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rickandmorty.model.pojo.Result
import com.rickandmorty.model.database.CharacterDao
import com.rickandmorty.remote.Retrofit

class CharacterRepository(private val dao: CharacterDao) {

    //private val services = RetrofitGames.getRetrofitInstance()
    val liveDataCharactersDB : LiveData<List<Result>> = dao.getAllCharactersDataBase()
    val liveDataCharResponse = MutableLiveData<List<Result>>()

    suspend fun getCharactersWithCoroutines( page :Int)  {
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
    }

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



  fun getCharacterByID(id: String) : LiveData<Result> {
       return dao.getCharacterByID(id)

  }
    fun getAllCharacter() : LiveData<List<Result> >{
        return dao.getAllCharactersDataBase()

    }

}