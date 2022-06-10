package com.rickandmorty.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.rickandmorty.model.pojo.Result
import com.rickandmorty.model.database.CharacterDao
import com.rickandmorty.remote.Retrofit

class CharacterRepository(private val dao: CharacterDao) {

    //private val services = RetrofitGames.getRetrofitInstance()
    val liveDataCharactersDB : LiveData<List<Result>> = dao.getAllCharactersDataBase()

    suspend fun getCharactersWithCoroutines( page :Int)  {
        try {
            val response = Retrofit.getRetrofitInstance().getAllCharacter(page )

            when(response.isSuccessful) {

                true -> response.body()?.let {
                    //inserte los personajes en la base de datos
                    dao.insertAllCharacter(it)
                    Log.d("repoCharacter", "$it")
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