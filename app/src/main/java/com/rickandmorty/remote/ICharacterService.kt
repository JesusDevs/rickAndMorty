package com.rickandmorty.remote

import com.rickandmorty.model.pojo.CharacterResponse
import com.rickandmorty.model.pojo.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterService {

    @GET("character")
    suspend fun getAllCharacter(@Query("page") page: Int) : Response<CharacterResponse>

}